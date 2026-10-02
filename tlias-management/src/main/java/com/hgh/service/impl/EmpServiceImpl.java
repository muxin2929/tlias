package com.hgh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hgh.mapper.EmpExprMapper;
import com.hgh.mapper.EmpMapper;
import com.hgh.pojo.*;
import com.hgh.service.EmpLogService;
import com.hgh.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hgh.utils.JwtUtils.generateJwt;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    // ========原始分页查询========
    /*@Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        //调用mapper层方法查询员工记录总数和分页记录
        Long total = empMapper.count();

        //mapper层方法查询员工记录总数和分页记录
        List<Emp> list = empMapper.list((page - 1) * pageSize, pageSize);

        //封装分页结果对象
        return new PageResult<>(total, list);
    }*/

    // ========PageHelper分页查询员工记录========
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        //调用mapper层方法执行分页查询
        List<Emp> list = empMapper.list(empQueryParam);

        //解析分页结果，封装分页结果对象
        PageInfo<Emp> p= new PageInfo<>(list);
        return new PageResult<Emp>(p.getTotal(),p.getList());

    }

    // ========保存员工记录========
    @Transactional(rollbackFor = Exception.class) //事务管理,默认运行时异常会回滚事务
    @Override
    public void save(Emp emp) {
        try {
            //保存员工记录
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            //保存经历记录
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //遍历经历记录，设置员工ID为当前员工ID
                exprList.forEach(expr -> expr.setEmpId(emp.getId()));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //保存日志记录
            EmpLog empLog = new EmpLog(null,LocalDateTime.now(),"新增员工："+emp.getName());
            empLogService.insertLog(empLog);
        }
    }

    // ========批量删除员工记录========
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        boolean success = false;
        try {
            //删除员工记录
            empMapper.deleteBatch(ids);
            //删除经历记录
            empExprMapper.deleteBatch(ids);
            success = true;
        } finally {
            //日志记录
            EmpLog empLog = new EmpLog(null,LocalDateTime.now(),"删除员工:" + ids + (success ? "成功" : "失败"));
            empLogService.insertLog(empLog);
        }

    }

    // ========根据id查询员工记录========
    @Override
    public Emp get(Integer id) {
        //根据id查询员工记录
        Emp emp = empMapper.getById(id);
        //根据员工ID查询经历记录
        List<EmpExpr> exprList = empExprMapper.getByEmpId(id);
        emp.setExprList(exprList);
        return emp;
    }

    // ========修改员工记录========
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        try {
            //根据id更新员工记录
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.updateById(emp);

            //根据员工ID更新经历记录
            //1.删除旧的经历记录
            empExprMapper.deleteBatch(List.of(emp.getId()));

            //2.保存新的经历记录
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //遍历经历记录，设置员工ID为当前员工ID
                exprList.forEach(expr -> expr.setEmpId(emp.getId()));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //保存日志记录
            EmpLog empLog = new EmpLog(null,LocalDateTime.now(),"修改员工："+emp.getName());
            empLogService.insertLog(empLog);
        }
    }

    @Override
    public List<Emp> list() {
        //查询所有员工记录
        return empMapper.selectList();
    }

    @Override
    public LoginInfo login(Emp emp) {
        //调用mapper层方法根据用户名查询员工记录
        Emp loginEmp = empMapper.loginSelect(emp);

        //判断员工是否存在，组装登录信息对象
        if(loginEmp!=null){
            log.info("登录成功,{}", loginEmp);

            //生成JWT token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginEmp.getId());
            claims.put("username", loginEmp.getUsername());
            String token = generateJwt(claims);
            return new LoginInfo(loginEmp.getId(),loginEmp.getUsername(),loginEmp.getName(),token);
        }

        //不存在员工记录
        return null;
    }
}
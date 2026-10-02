package com.hgh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hgh.exception.ExistException;
import com.hgh.mapper.ClazzMapper;
import com.hgh.mapper.StudentMapper;
import com.hgh.pojo.Clazz;
import com.hgh.pojo.ClazzQueryParam;
import com.hgh.pojo.PageResult;
import com.hgh.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        //设置分页参数
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());

        //调用mapper层方法执行分页查询,遍历结果集，设置status属性
        List<Clazz> clazzList = clazzMapper.getByQueryParam(clazzQueryParam);
        clazzList.forEach(clazz -> {
            if (clazz.getBeginDate().isAfter(LocalDate.now())) {
                clazz.setStatus("未开班");
            } else if (clazz.getEndDate().isAfter(LocalDate.now())) {
                clazz.setStatus("在读中");
            } else {
                clazz.setStatus("已结课");
            }
        });

        //解析分页结果，封装分页结果对象
        PageInfo<Clazz> pageInfo = new PageInfo<>(clazzList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void delete(Integer id) {

        //判断班级下是否有学生
        int studentCount = studentMapper.selectStudentCount(id);
        if (studentCount > 0) {
            throw new ExistException("抱歉，该班级下有学生, 不能直接删除");
        }else{
            clazzMapper.deleteById(id);
        }
    }

    @Override
    public void save(Clazz clazz) {
        //设置创建时间和修改时间
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        //调用mapper层方法执行插入操作
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz get(Integer id) {
        return clazzMapper.selectById(id);
    }

    @Override
    public void update(Clazz clazz) {
        //设置修改时间
        clazz.setUpdateTime(LocalDateTime.now());
        //调用mapper层方法执行更新操作
        clazzMapper.update(clazz);
    }

    @Override
    public List<Clazz> list() {
        return clazzMapper.selectAll();
    }
}

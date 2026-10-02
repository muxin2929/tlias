package com.hgh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hgh.mapper.EmpMapper;
import com.hgh.mapper.OperateLogMapper;
import com.hgh.mapper.StudentMapper;
import com.hgh.pojo.ClazzCountOption;
import com.hgh.pojo.JobOption;
import com.hgh.pojo.OperateLog;
import com.hgh.pojo.PageResult;
import com.hgh.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public JobOption getEmpJobData() {
        //1.调用Mapper层方法查询员工职位数据
        List<Map<String,Object>> list = empMapper.countEmpJobData();

        //2.组装JobOption对象
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("job_name")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("emp_count")).toList();
        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }

    @Override
    public ClazzCountOption getStudentCountData() {
        //1.调用Mapper层方法查询学生学历数据
        List<Map<String,Object>> list = studentMapper.count();
        //2.组装JobOption对象
        List<Object> clazzList = list.stream().map(dataMap -> dataMap.get("name")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("value")).toList();
        return new ClazzCountOption(clazzList,dataList);
    }

    @Override
    public PageResult page(Integer page, Integer pageSize) {
        //设置分页参数
        PageHelper.startPage(page, pageSize);
        //调用Mapper层方法查询操作日志分页数据
        List<OperateLog> list = operateLogMapper.selectPage();
        //3.组装PageResult对象
        PageInfo<OperateLog> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }
}

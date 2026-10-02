package com.hgh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hgh.mapper.StudentMapper;
import com.hgh.pojo.PageResult;
import com.hgh.pojo.Student;
import com.hgh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> page(Student student, Integer page, Integer pageSize) {
        //设置分页参数
        PageHelper.startPage(page == null ? 1 : page, pageSize == null ? 10 : pageSize);
        //调用Mapper查询学生
        List<Student> students = studentMapper.selectByPage(student);
        //返回分页结果
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void delete(Integer[] ids) {
        studentMapper.deleteBatch(ids);
    }

    @Override
    public void insert(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
       }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }

    @Override
    public Student selectById(Integer id) {
        return studentMapper.selectById(id);
    }

    @Override
    public void updateViolationScore(Integer id, Integer score) {
        Student student = studentMapper.selectById(id);
        student.setViolationCount((short) (student.getViolationCount() + 1));
        student.setViolationScore((short) (student.getViolationScore() + score));
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }
}

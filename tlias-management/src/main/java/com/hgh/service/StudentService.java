package com.hgh.service;

import com.hgh.pojo.Clazz;
import com.hgh.pojo.PageResult;
import com.hgh.pojo.Student;

public interface StudentService {
    PageResult<Student> page(Student student, Integer page, Integer pageSize);

    void delete(Integer[] ids);

    void insert(Student student);

    void update(Student student);

    Student selectById(Integer id);

    void updateViolationScore(Integer id, Integer score);
}

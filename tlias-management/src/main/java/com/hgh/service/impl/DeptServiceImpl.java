package com.hgh.service.impl;

import com.hgh.exception.ExistException;
import com.hgh.mapper.DeptMapper;
import com.hgh.pojo.Dept;
import com.hgh.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        int rows=deptMapper.deleteById(id);
        if(rows==0){
            throw new ExistException("对不起，部门下有员工，不能删除！");
        }
    }

    @Override
    public void add(Dept dept) {
        //补全基础信息
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        //调用mapper层方法
        deptMapper.add(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        //补全基础信息
        dept.setUpdateTime(LocalDateTime.now());

        //调用mapper层方法
        deptMapper.update(dept);
    }
}

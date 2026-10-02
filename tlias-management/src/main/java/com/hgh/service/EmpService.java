package com.hgh.service;

import com.hgh.pojo.Emp;
import com.hgh.pojo.EmpQueryParam;
import com.hgh.pojo.LoginInfo;
import com.hgh.pojo.PageResult;

import java.util.List;

public interface EmpService {
    PageResult<Emp> page(EmpQueryParam empQueryRaram);

    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp get(Integer id);

    void update(Emp emp);

    List<Emp> list();

    LoginInfo login(Emp emp);
}

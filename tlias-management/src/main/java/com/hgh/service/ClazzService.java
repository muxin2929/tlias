package com.hgh.service;

import com.hgh.pojo.Clazz;
import com.hgh.pojo.ClazzQueryParam;
import com.hgh.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    void delete(Integer id);

    void save(Clazz clazz);

    Clazz get(Integer id);

    void update(Clazz clazz);

    List<Clazz> list();
}

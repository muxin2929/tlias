package com.hgh.mapper;

import com.hgh.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    //方式一
   /* @Results({
            @Result(column = "update_time",property = "updateTime"),
            @Result(column = "create_time",property = "createTime")
    })*/

    //方式二
    //@Select("select id,name,create_time createTime,update_time updateTime from dept order by update_time desc")
    @Select("select id,name,create_time,update_time from dept order by update_time desc")

    //方式三
    //开启驼峰命名射射 (create_time对应createTime)
    List<Dept> findAll();

    @Delete("delete from dept where id = #{id} and NOT EXISTS (select id from emp where dept_id = #{id})")
    int deleteById(Integer id);

    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void add(Dept dept);

    //根据id查询部门
    @Select("select id,name,create_time,update_time from dept where id = #{id}")
    Dept getById(Integer id);

    //修改部门
    @Update("update dept set name = #{name},update_time = now() where id = #{id}")
    void update(Dept dept);
}

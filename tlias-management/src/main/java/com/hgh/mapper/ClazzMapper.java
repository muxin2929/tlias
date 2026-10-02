package com.hgh.mapper;

import com.hgh.pojo.Clazz;
import com.hgh.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClazzMapper {
    //根据查询参数查询班级列表
    List<Clazz> getByQueryParam(ClazzQueryParam clazzQueryParam);

    @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time) values(#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void insert(Clazz clazz);

    Clazz selectById(Integer id);

    void update(Clazz clazz);

    @Select("select * from clazz")
    List<Clazz> selectAll();
}

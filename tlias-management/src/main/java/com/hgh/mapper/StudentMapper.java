package com.hgh.mapper;

import com.hgh.pojo.Clazz;
import com.hgh.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    @Select("select count(*) from student where clazz_id = #{id}")
    int selectStudentCount(Integer id);

    List<Student> selectByPage(Student student);

    void deleteBatch(Integer[] ids);

    void insert(Student student);

    void update(Student student);

    @Select("select * from student where id = #{id}")
    Student selectById(Integer id);

    List<Map<String, Object>> countStudentDegreeData();

    List<Map<String, Object>> count();
}

package com.hgh.mapper;

import com.hgh.pojo.Emp;
import com.hgh.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

    // =========原始分页查询========

       // 分页查询员工记录总数
   /* @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
    Long count();*/

    // 分页查询员工记录
    /*@Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id " +
            "order by e.update_time desc limit #{start},#{pageSize}")
    List<Emp> list(Integer start, Integer pageSize);*/

    // =========PageHelper分页查询========
    //注意结尾不能加分号
    //@Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc ")
    List<Emp> list(EmpQueryParam empQueryParam);

    @Options(useGeneratedKeys = true, keyProperty = "id") //开启自动获取主键值
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "value(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},now(),now());")
    void insert(Emp emp);

    // ========批量删除员工记录========
    void deleteBatch(List<Integer> ids);

    // ========根据id查询员工记录========
    @Select("select id,username,name,gender,phone,job,salary,image,entry_date,dept_id " +
            "from emp where id = #{id}")
    Emp getById(Integer id);

    // ========修改员工记录========
    void updateById(Emp emp);

    // ========统计员工职位数据========
    @MapKey("job_name")
    List<Map<String,Object>> countEmpJobData();

    // ========统计员工性别数据========
    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();

    @Select("select * from emp")
    List<Emp> selectList();

    @Select("select id,username,name from emp where username = #{username} and password = #{password}")
    Emp loginSelect(Emp emp);
}
package com.hgh.mapper;

import com.hgh.pojo.EmpExpr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    //批量保存经历记录
    void insertBatch(List<EmpExpr> exprList);

    void deleteBatch(List<Integer> empIds);

    @Select("select begin,end,company,job from emp_expr where emp_id = #{empId}")
    List<EmpExpr> getByEmpId(Integer empId);
}

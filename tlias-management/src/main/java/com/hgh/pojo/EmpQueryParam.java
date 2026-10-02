package com.hgh.pojo;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 员工查询参数封装类
 */
@Data
public class EmpQueryParam {
    /** 页码，默认值为 1 */
    private Integer page = 1;
    /** 每页条数，默认值为 10 */
    private Integer pageSize = 10;
    /** 员工姓名（模糊查询） */
    private String name;
    /** 员工性别，1 表示男，2 表示女 */
    private Integer gender;
    /** 入职时间范围 - 起始日期，格式：yyyy-MM-dd */
    private @DateTimeFormat(pattern = "YYYY-MM-DD")
    LocalDate begin;
    /** 入职时间范围 - 结束日期，格式：yyyy-MM-dd */
    private @DateTimeFormat(pattern = "YYYY-MM-DD")
    LocalDate end;
}
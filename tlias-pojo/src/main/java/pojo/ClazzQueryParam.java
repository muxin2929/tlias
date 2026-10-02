package pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ClazzQueryParam {
    /** 页码，默认值为 1 */
    private Integer page = 1;
    /** 每页条数，默认值为 10 */
    private Integer pageSize = 10;
    /** 班级名称（模糊查询） */
    private String name;
    /** 班级时间范围 - 起始日期，格式：yyyy-MM-dd */
    private @DateTimeFormat(pattern = "YYYY-MM-DD")
    LocalDate begin;
    /** 班级时间范围 - 结束日期，格式：yyyy-MM-dd */
    private @DateTimeFormat(pattern = "YYYY-MM-DD")
    LocalDate end;
}

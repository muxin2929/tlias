package com.hgh.service;

import com.hgh.pojo.ClazzCountOption;
import com.hgh.pojo.JobOption;
import com.hgh.pojo.PageResult;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();

    List<Map<String, Object>> getStudentDegreeData();
    ClazzCountOption getStudentCountData();

    PageResult page(Integer page, Integer pageSize);
}

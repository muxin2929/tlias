package com.hgh.controller;

import com.hgh.pojo.OperateLog;
import com.hgh.pojo.PageResult;
import com.hgh.pojo.Result;
import com.hgh.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@Slf4j
public class OperateLogController {
    private final ReportService reportService;

    public OperateLogController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping("/page")
    public Result page(Integer page, Integer pageSize) {
        log.info("查询操作日志分页，page: {}, pageSize: {}", page, pageSize);
        PageResult operateLogResult = reportService.page(page, pageSize);
        return Result.success(operateLogResult);
    }
}

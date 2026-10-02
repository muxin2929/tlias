package com.hgh.controller;

import com.hgh.anno.Log;
import com.hgh.pojo.Emp;
import com.hgh.pojo.EmpQueryParam;
import com.hgh.pojo.PageResult;
import com.hgh.pojo.Result;
import com.hgh.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/list")
    public Result list() {
        log.info("查询所有员工");
        List<Emp> emp = empService.list();
        return Result.success(emp);
    }

    // ========分页查询员工记录========
    @GetMapping
    Result page(EmpQueryParam empQueryParam) {
        log.info("分页查询:{}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    //根据id查询员工记录
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        log.info("查询员工:{}", id);
        Emp emp = empService.get(id);
        return Result.success(emp);
    }

    // ========新增员工记录========
    @Log
    @PostMapping
    public Result add(@RequestBody Emp emp) {
        log.info("新增员工:{}", emp);
        empService.save(emp);
        return Result.success();
    }

    // ========批量删除员工记录========
    @Log
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除员工:{}", ids);
        empService.delete(ids);
        return Result.success();
    }

    // ========修改员工记录========
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("修改员工:{}", emp);
        empService.update(emp);
        return Result.success();
    }
}
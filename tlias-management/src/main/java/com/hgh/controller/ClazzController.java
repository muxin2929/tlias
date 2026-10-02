package com.hgh.controller;

import com.hgh.anno.Log;
import com.hgh.pojo.Clazz;
import com.hgh.pojo.ClazzQueryParam;
import com.hgh.pojo.PageResult;
import com.hgh.pojo.Result;
import com.hgh.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("分页查询:{}", clazzQueryParam);
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除班级:{}", id);
        clazzService.delete(id);
        return Result.success();
    }

    @Log
    @PostMapping
    public Result save(@RequestBody Clazz clazz) {
        log.info("保存班级:{}", clazz);
        clazzService.save(clazz);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        log.info("查询班级:{}", id);
        Clazz clazz = clazzService.get(id);
        return Result.success(clazz);
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("更新班级:{}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list() {
        log.info("查询班级列表");
        List<Clazz> clazzList = clazzService.list();
        return Result.success(clazzList);
    }
}
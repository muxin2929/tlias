package com.hgh.controller;

import com.hgh.anno.Log;
import com.hgh.pojo.PageResult;
import com.hgh.pojo.Result;
import com.hgh.pojo.Student;
import com.hgh.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result page(Student student, Integer page, Integer pageSize) {
        log.info("分页查询学生");
        PageResult<Student> pageResult = studentService.page(student, page, pageSize);
        return Result.success(pageResult);
    }

    @Log
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Integer[] ids) {
        log.info("删除学生{}", (Object) ids);
        studentService.delete(ids);
        return Result.success();
    }

    @Log
    @PostMapping
    public Result insert(@RequestBody Student student) {
        log.info("插入学生{}", student);
        studentService.insert(student);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        log.info("根据ID查询学生{}", id);
        Student student = studentService.selectById(id);
        return Result.success(student);
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("更新学生{}", student);
        studentService.update(student);
        return Result.success();
    }

    @Log
    @PutMapping("/violation/{id}/{score}")
    public Result updateViolationScore(@PathVariable Integer id, @PathVariable Integer score) {
        log.info("更新学生{},违规分数为{}", id, score);
        studentService.updateViolationScore(id, score);
        return Result.success();
    }
}
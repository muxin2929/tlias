package com.hgh.controller;

import com.hgh.pojo.Emp;
import com.hgh.pojo.LoginInfo;
import com.hgh.pojo.Result;
import com.hgh.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping
    public Result login(@RequestBody Emp emp){
        log.info("登录员工:{}", emp);
        LoginInfo loginInfo = empService.login(emp);
        if(loginInfo!=null){
            return Result.success(loginInfo);
        }
        return Result.error("登录失败,用户名或密码错误");
    }
}

package com.hgh.controller;

import com.hgh.anno.Log;
import com.hgh.pojo.Result;
import com.hgh.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

   /* @PostMapping
    public Result upload(String name, Integer age, MultipartFile file) throws IOException {
        log.info("name: {}, age: {}, file: {}", name, age, file);
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //新文件名
        String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extendName;
        //保存文件
        file.transferTo(new File("D:\\image\\"+newFilename));
        return Result.success(newFilename);
    }*/

    // ========上传文件到OSS========
    @Log
    @PostMapping
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传: {}", file);
        //文件上传到OSS
        String url = aliyunOSSOperator.upload(file.getBytes(), Objects.requireNonNull(file.getOriginalFilename()));
        log.info("文件上传到OSS,url: {}", url);
        return Result.success(url);
    }
}
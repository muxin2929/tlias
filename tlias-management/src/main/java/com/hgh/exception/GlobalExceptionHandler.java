package com.hgh.exception;

import com.hgh.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

//全局异常处理器
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ========通用异常兜底========
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("程序出错啦~", e);
        return Result.error("出错了请联系管理员~");
    }

    // ========唯一约束冲突（如用户名重复）========
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("程序出错啦~");
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error(arr[2] + "已存在");
    }

    //自定义外键约束异常
    @ExceptionHandler
    public Result handleForeignKeyConstraint(ExistException e) {
        log.error("关联存在不能直接删除", e);
        return Result.error(e.getMessage());
    }

    // ========数据完整性异常（外键约束、非空约束等）========
    @ExceptionHandler
    public Result handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("数据完整性异常: ", e);
        String msg = e.getMessage();
        if (msg != null && msg.contains("foreign key constraint fails")) {
            return Result.error("该数据被其他数据引用，无法操作");
        }
        if (msg != null && msg.contains("cannot be null")) {
            return Result.error("必填字段不能为空");
        }
        return Result.error("数据完整性校验失败");
    }

    // ========上传文件大小超限========
    @ExceptionHandler
    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("文件大小超出限制: ", e);
        return Result.error("上传文件大小超出限制");
    }

    // ========请求方式不支持（如该用PUT却用了POST）========
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("请求方式不支持: ", e);
        return Result.error("不支持的请求方式: " + e.getMethod());
    }

    // ========请求体JSON解析失败（格式错误或为空）========
    @ExceptionHandler
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("请求体解析失败: ", e);
        return Result.error("请求体格式错误，请检查JSON数据");
    }

    // ========参数类型不匹配（如id应为Integer却传了字符串）========
    @ExceptionHandler
    public Result handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数类型不匹配: ", e);
        return Result.error("参数类型错误: " + e.getName() + " 应为 " + e.getRequiredType().getSimpleName());
    }

    // ========缺少必填请求参数========
    @ExceptionHandler
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少必填参数: ", e);
        return Result.error("缺少必填参数: " + e.getParameterName());
    }

    // ========请求的接口不存在========
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("接口不存在: ", e);
        return Result.error("请求的接口不存在: " + e.getRequestURL());
    }

    // ========请求的接口不存在（Spring Boot 3 默认行为，优先匹配）========
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleNoResourceFoundException(NoResourceFoundException e) {
        log.error("接口不存在: {}", e.getResourcePath());
        return Result.error("请求的接口不存在: " + e.getResourcePath());
    }

    // ========参数校验失败（需引入validation依赖后生效）========
    @ExceptionHandler
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验失败: ", e);
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Result.error(msg);
    }

    // ========非法参数========
    @ExceptionHandler
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数: ", e);
        return Result.error("参数错误: " + e.getMessage());
    }
}
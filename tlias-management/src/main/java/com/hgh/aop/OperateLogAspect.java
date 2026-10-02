package com.hgh.aop;

import com.hgh.context.UserContext;
import com.hgh.mapper.OperateLogMapper;
import com.hgh.pojo.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志切面
 * 拦截标注了 @Log 注解的方法，自动记录操作日志到 operate_log 表
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 环绕通知：记录操作日志
     * 切点：所有标注了 @com.hgh.anno.Log 注解的方法
     */
    @Around("@annotation(com.hgh.anno.Log)")
    public Object recordOperateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录方法开始时间，用于计算执行耗时
        long start = System.currentTimeMillis();

        // 执行目标方法，获取返回值
        Object result = joinPoint.proceed();

        // 计算方法执行耗时（毫秒）
        long costTime = System.currentTimeMillis() - start;

        // 日志记录失败不影响业务方法正常返回
        try {
            OperateLog operateLog = new OperateLog();

            operateLog.setOperateEmpId(UserContext.getEmpId());              // 操作人ID（从ThreadLocal获取）
            operateLog.setOperateTime(LocalDateTime.now());                  // 操作时间
            operateLog.setClassName(joinPoint.getTarget().getClass().getName()); // 目标类的全类名
            operateLog.setMethodName(((MethodSignature) joinPoint.getSignature()).getName()); // 目标方法名
            operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs())); // 方法运行时参数
            operateLog.setReturnValue(result == null ? null : result.toString()); // 方法返回值
            operateLog.setCostTime(costTime);                                // 方法执行时长

            operateLogMapper.insert(operateLog);
        } catch (Exception e) {
            log.error("记录操作日志失败", e);
        }

        return result;
    }
}
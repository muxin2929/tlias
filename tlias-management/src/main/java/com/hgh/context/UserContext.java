package com.hgh.context;

/**
 * 基于 ThreadLocal 的当前登录用户上下文
 * 在 TokenInterceptor 中解析 JWT 后存入，请求结束后清理
 */
public class UserContext {

    private static final ThreadLocal<Integer> CURRENT_EMP_ID = new ThreadLocal<>();

    public static void setEmpId(Integer empId) {
        CURRENT_EMP_ID.set(empId);
    }

    public static Integer getEmpId() {
        return CURRENT_EMP_ID.get();
    }

    /**
     * 清理当前线程的用户信息，必须在请求结束时调用，防止线程池复用导致数据串用
     */
    public static void clear() {
        CURRENT_EMP_ID.remove();
    }
}
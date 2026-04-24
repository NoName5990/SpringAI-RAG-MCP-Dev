package com.jixiaoliu.utils;

/**
 * @ClassName Resp
 * @Author liujxiao
 * @Version 1.0
 * @Description 自定义响应数据结构
 *              200：表示成功
 *              400：常规异常
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 * 				556: 用户qq校验异常
 * 			    557: 校验用户是否在CAS登录，用户门票的校验
 * @date 2026/4/23 上午10:43
 */
public class Resp<T> {
    private static final String RESP_OK = "200";
    private static final String EXCEPTION = "400";
    private static final String ERROR_GENERAL = "500";
    private static final String ERROR_BEAN_VALIDATION = "501";
    private static final String ERROR_TOKEN_INVALID = "502";
    private static final String ERROR_EXCEPTION_THROWN = "555";
    private static final String ERROR_QQ_VERIFY = "556";
    private static final String ERROR_CAS_TICKET = "557";
    private T data;
    private String msg;
    private String status;

    public Resp(T data, String msg, String status) {
        this.data = data;
        this.msg = msg;
        this.status = status;
    }

    public static <T> Resp<T> success(T data) {
        return success(data,"ok");
    }

    public static <T> Resp<T> success() {
        return new Resp<T>(null, "ok", RESP_OK);
    }

    public static <T> Resp<T> success(T data, String msg) {
        return new Resp<T>(data,msg,RESP_OK);
    }

    public static <T> Resp<T> error(String msg, String status) {
        return new Resp<T>(null, msg, status);
    }

    public static <T> Resp<T> error(String msg) {
        return error(msg, EXCEPTION);
    }

}

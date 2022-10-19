package com.obeast.common.base;


public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    ARGS_IS_NULL(5005, "参数为空请检测或者参数是无效值"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    VALIDATE_FAILED(402, "参数检验失败"),
    FORBIDDEN(403, "没有相关权限"),
    RESPONSE_PACK_ERROR(501, "Json打包失败"),
    INITIAL_PWD(502, "请修改默认密码"),
    NO_SUCH_WX_USER(503, "无此微信用户");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

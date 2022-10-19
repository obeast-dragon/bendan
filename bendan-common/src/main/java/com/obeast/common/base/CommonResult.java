package com.obeast.common.base;



import lombok.Data;

/**
 * @author wxl
 * Date 2022/8/17 14:13
 * @version 1.0
 * Description: 全局返回值类
 */
@Data
public class CommonResult<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    protected CommonResult() {
    }

    protected CommonResult(Integer code, String message, T data, Boolean isSuccess) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = isSuccess;
    }

    /**
     * @description: 无返回值
     * @author wxl
     * @date 2022/7/15 17:56
     * @return null
     **/
    public static <T> CommonResult<T> success() {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setSuccess(true);
        commonResult.setCode(200);
        commonResult.setMessage("operation successfully");
        return commonResult;
    }


    /**
     * Description: 成功有描述
     * @author wxl
     * Date: 2022/8/17 14:12
     * @param message msg
     * @return com.worldintek.common.api.R<T>
     */
    public static <T> CommonResult<T> success(String message) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setSuccess(true);
        commonResult.setCode(200);
        commonResult.setMessage(message);
        return commonResult;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data, true);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data, true);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> error(IErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null, false);
    }



    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> CommonResult<T> error(IErrorCode errorCode, String message) {
        return new CommonResult<>(errorCode.getCode(), message, null, false);
    }

    /**
     * 失败返回结果
     * @param message 错误信息
     * @param data 返回数据
     */
    public static <T> CommonResult<T> error(String message, T data) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), message, data, false);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> error(String message) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), message, null, false);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> error() {
        return error(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return error(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null, false);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data, false);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(String message) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), message, null, false);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data, false);
    }

    public long getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}


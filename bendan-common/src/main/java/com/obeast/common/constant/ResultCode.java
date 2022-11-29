package com.obeast.common.constant;

import com.obeast.common.base.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;


/**
 * @author wxl
 * Date 2022/10/31 9:57
 * @version 1.0
 * Description: 定义基本的错误码
 */
@Getter
public enum ResultCode implements IErrorCode {

    /**
     * 操作成功 200
     */
    SUCCESS(HttpServletResponse.SC_OK, "操作成功", "Success"),

    /**
     * 业务异常 400
     */
    FAILURE(HttpServletResponse.SC_BAD_REQUEST, "业务异常", "Failure"),

    /**
     * 消息不能读取 400
     */
    MSG_NOT_READABLE(HttpServletResponse.SC_BAD_REQUEST, "消息不能读取", "Not readable"),

    /**
     * 缺少必要请求参数 400
     */
    PARAM_MISS(HttpServletResponse.SC_BAD_REQUEST, "缺少必要的请求参数", "Params bad request"),


    /**
     * 请求参数类型错误 400
     */
    PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数类型错误", "Params bad request"),

    /**
     * 请求参数绑定错误 400
     */
    PARAM_BIND_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数绑定错误", "Params bind request"),


    /**
     * 参数校验失败 400
     */
    PARAM_VALID_ERROR(HttpServletResponse.SC_BAD_REQUEST, "参数校验失败", "Params valid fail"),

    /**
     * 参数校验失败 400
     */
    VALID_CODE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "验证码错误", "Params valid fail"),

    /**
     * 请求未授权 401
     */
    UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "未授权,请登录", "unauthorized"),

    /**
     * 请求被拒绝 403
     */
    REQ_REJECT(HttpServletResponse.SC_FORBIDDEN, "请求被拒绝或没有相关权限", "Request forbidden"),


    /**
     * 404 没找到请求
     */
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "404 没找到请求", "Not found"),


    /**
     * 不支持当前请求方法 405
     */
    METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "不支持当前请求方法", "Not supported"),

    /**
     * 不支持当前媒体类型 415
     */
    MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "不支持当前媒体类型", "Media type not supported"),



    INTERNAL_RESOURCE_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "资源服务器异常, 请联系管理员", "RESOURCE SERVER ERROR, please contact the admin"),

    /**
     * 服务器异常 500
     */
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常, 请联系管理员", "Operation fail, please contact the admin"),

    /**
     * 远端服务器为启用或者调用失败 503
     * */
    REMOTE_SERVICE_NOT_ACTIVATED(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "远程服务调用失败或者还未启动", "The remote service call failed or has not been started"),


    /**
     * 数据重复保存 600
     */
    DUPLICATE_KEY_ERROR(600, "数据重复保存", "Data duplicate"),

    /**
     * 远程请求错误 601
     */
    FEIGN_ERROR(601, "远程请求错误", "remote request fail"),
    ;


    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
        this.messageEn = getMessageEn();
    }

    ResultCode(int code, String message, String messageEn) {
        this.code = code;
        this.message = message;
        this.messageEn = messageEn;
    }

    /**
     * code编码
     */
    final int code;

    /**
     * 中文信息描述
     */
    final String message;

    /**
     * 英文信息
     *
     * @since 2022.10.24
     */
    final String messageEn;
}

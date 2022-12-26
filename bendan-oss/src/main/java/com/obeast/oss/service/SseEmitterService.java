package com.obeast.oss.service;

import com.obeast.oss.domain.FlyweightRes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author wxl
 * @version 1.0
 * @description: SseEmitter发送信息的服务类
 * @date 2022/7/8 13:34
 */
public interface SseEmitterService {
    /**
     * 创建连接
     *
     * @param clientId 客户端ID
     */
    SseEmitter createConnect(String clientId);


    /**
     * 根据客户端id获取SseEmitter对象
     *
     * @param clientId 客户端ID
     */
    SseEmitter getSseEmitterByClientId(String clientId);


    /**
     * 发送消息给所有客户端
     *
     * @param msg 消息内容
     */
    void sendMessageToAllClient(String msg);

    /**
     * 给指定客户端发送消息
     *
     * @param clientId 客户端ID
     * @param msg      消息内容
     */
    void sendMessageToOneClient(String clientId, String msg);


    /**
     * Description: 往共享的map中放数据
     * @author wxl
     * Date: 2022/9/21 15:34
     * @param clientId client Id
     * @param flyweightRes response entry
     */
    void sendResMapToOneClient(String clientId, FlyweightRes flyweightRes);

    /**
     * 关闭连接
     *
     * @param clientId 客户端ID
     */
    void closeConnect(String clientId);

    /**
     * @description: 服务器给客户端发送消息
     * @author wxl
     * @date 2022/7/21 16:00
     * @param userUuid 客户端ID
     **/
    void sendMessage(String userUuid);
}

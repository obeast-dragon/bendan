package com.obeast.chat.domain;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 * Date 2022/12/27 13:07
 * @version 1.0
 * Description: 保存连接对象的实体 （该类保存的是线程不能持久化）
 */
@Slf4j
@Component
public class ChatChannelGroup {
    //保存连接对象的集合
    /**
     * key 设备id
     * channel 连接通道
     * */
    public final Map<String, Channel> connects = new ConcurrentHashMap<>();

    /**
     * Description: 添加
     * @author wxl
     * Date: 2022/12/27 13:06
     * @param userUuid  uuid
     * @param channel channel
     */
    public void addChannel(String userUuid, Channel channel) {
        log.info("{}用户建立通道", userUuid);
        connects.put(userUuid, channel);
    }

    /**
     * @description: 查
     * @author wxl
     * @date 2022/7/19 13:54
     * @param userUuid 设备id
     * @return channel 通道
     **/
    public Channel getChannel(String userUuid) {
        return connects.get(userUuid);
    }

    /**
     * @description: 删除
     * @author wxl
     * @date 2022/7/19 13:56
     * @param userUuid 设备id
     * @return boolean
     **/
    public boolean removeChannel(String userUuid) {
        return connects.remove(userUuid) != null;
    }


}

package com.obeast.chat.handler;

import com.obeast.chat.domain.ChatChannelGroup;
import com.obeast.chat.domain.ChatMsg;
import com.obeast.chat.entity.ChatRecordEntity;
import com.obeast.chat.service.ChatRecordService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;

/**
 * @author wxl
 * Date 2022/12/27 12:45
 * @version 1.0
 * Description: 聊天消息处理器
 */
@Slf4j
public class ChatMsgHandler extends SimpleChannelInboundHandler<ChatMsg> {

    private final ChatChannelGroup chatChannelGroup;

    private final RabbitTemplate rabbitTemplate;

    private final ChatRecordService chatRecordService;



    public ChatMsgHandler(ChatChannelGroup chatChannelGroup, RabbitTemplate rabbitTemplate, ChatRecordService chatRecordService) {
        this.chatChannelGroup = chatChannelGroup;
        this.rabbitTemplate = rabbitTemplate;
        this.chatRecordService = chatRecordService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMsg msg) throws Exception {

        log.info("ChatTextMsgHandler客户端发送文本的聊天消息：" + msg.getContent());
        String fromUuid = msg.getFromUuid();
        String toUuid = msg.getToUuid();
        if (!msg.getContent().equals("")){
            //      消息入DB库
            String content = msg.getContent();
            ChatRecordEntity chatRecordEntity = new ChatRecordEntity();
            chatRecordEntity.setFromUuid(fromUuid);
            chatRecordEntity.setToUuid(toUuid);
            chatRecordEntity.setContent(content);
            chatRecordEntity.setSendType(msg.getSendType());
            chatRecordEntity.setSendTime(new Date());
            chatRecordService.save(chatRecordEntity);
            //查询toId是否存在
            Channel channel = chatChannelGroup.getChannel(toUuid);
            log.info("-------------------------------->消息已经入DB库");
            try {
                if (channel.isOpen()) {
                    //对方在线，才做rabbitMq转发
                    log.info("channel is open is ok");
                    log.info("对方在线通过RabbitMq发送");
                    //通过rabbitmq转发出去
                    rabbitTemplate.convertAndSend("ws_exchange", "", msg);
                }
            }catch (Exception e){
                log.info("对方不在线----------------->do nothing");
            }
        }else {
            log.warn("message is null");
        }
    }
}

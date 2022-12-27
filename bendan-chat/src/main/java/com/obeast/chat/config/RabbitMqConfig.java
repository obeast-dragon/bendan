package com.obeast.chat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author wxl
 * Date 2022/12/27 13:06
 * @version 1.0
 * Description:
 */
@Configuration
public class RabbitMqConfig {

    @Value("${netty.port}")
    private Integer port;

    @Bean
    public Queue queue(){
        return new Queue("ws_queue_" + port);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("ws_exchange");
    }

    @Bean
    public Binding queueToExchange () {
        return BindingBuilder
                .bind(queue())
                .to(fanoutExchange());
    }
}

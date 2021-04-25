package com.jrx.cloud.websocket.api.config;

import com.jrx.cloud.assembly.enums.DataTopicEnum;
import com.jrx.cloud.assembly.mq.RoutingDestination;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hongjc
 * @version 1.0  2020/8/10
 */
@Configuration
public class RabbitMqConfig {


    // 发布
//    @Bean
//    public Queue autoDeleteQueue() {
//        return new AnonymousQueue();
//    }

//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(RoutingDestination.DEFAULT_EXCHANGE);
//    }

    // 发布
//    @Bean
//    public Binding bindingExchangeCmdNotice(Queue autoDeleteQueue, TopicExchange exchange) {
//        return BindingBuilder.bind(autoDeleteQueue).to(exchange).with(DataTopicEnum.GATEWAY_CMD_NOTICE.getDestination());
//    }


    // 订阅
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(RoutingDestination.DEFAULT_EXCHANGE);
    }
    // 订阅
//    @Bean
//    public Queue cmdRespNoticeQueue() {
//        return new Queue(RabbitMqConstants.QUEUE_CMD_NOTICE);
//    }

    // 订阅
//    @Bean
//    public Binding bindingExchangeCmdNotice(Queue cmdRespNoticeQueue, TopicExchange exchange) {
//        return BindingBuilder.bind(cmdRespNoticeQueue).to(exchange).with(DataTopicEnum.GATEWAY_CMD_NOTICE.getDestination());
//    }
}

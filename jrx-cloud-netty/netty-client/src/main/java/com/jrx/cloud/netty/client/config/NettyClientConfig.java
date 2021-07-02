package com.jrx.cloud.netty.client.config;

import com.jrx.cloud.netty.common.messgae.MessageDispatcher;
import com.jrx.cloud.netty.common.messgae.MessageHandlerContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author x
 * @version 1.0  2021/6/30
 */
@Configuration
public class NettyClientConfig {

    @Bean
    public MessageHandlerContainer messageHandlerContainer() {
        return new MessageHandlerContainer();
    }

    @Bean
    public MessageDispatcher messageDispatcher() {
        return new MessageDispatcher();
    }
}

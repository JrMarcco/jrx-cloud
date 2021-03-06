package com.jrx.cloud.netty.server.config;

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
@RequiredArgsConstructor
public class NettyServerConfig {

    private final ApplicationContext applicationContext;

    @Bean
    public MessageHandlerContainer messageHandlerContainer(ApplicationContext applicationContext) {
        return new MessageHandlerContainer(applicationContext);
    }

    @Bean
    public MessageDispatcher messageDispatcher(MessageHandlerContainer messageHandlerContainer) {
        return new MessageDispatcher(messageHandlerContainer);
    }
}

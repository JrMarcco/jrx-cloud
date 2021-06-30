package com.jrx.cloud.netty.client.config;

import com.jrx.cloud.netty.client.handler.NettyClientHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Invocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @author x
 * @version 1.0  2021/6/30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyClient {

    private static final Integer RECONNECT_SECONDS = 20;

    @Value("${netty.server.host:127.0.0.1}")
    private String serverHost;
    @Value("${netty.server.port:8888}")
    private Integer serverPort;

    private final NettyClientHandlerInitializer nettyClientHandlerInitializer;

    /**
     * 线程组，用于客户端对服务端的连接、数据读写
     */
    private final EventLoopGroup eventGroup = new NioEventLoopGroup();
    /**
     * Netty Client Channel
     */
    private volatile Channel channel;

    /**
     * 启动 Netty Client
     */
    @PostConstruct
    public void start() {
        // 创建 Bootstrap 对象，用于 Netty Client 启动
        var bootstrap = new Bootstrap();
        bootstrap.group(eventGroup) // 设置 EventLoopGroup 对象
                .channel(NioSocketChannel.class)  // 指定 Channel 为客户端 NioSocketChannel
                .remoteAddress(serverHost, serverPort) // 指定连接服务器的地址
                .option(ChannelOption.SO_KEEPALIVE, true) // TCP KeepAlive 机制，实现 TCP 层级的心跳保活功能
                .option(ChannelOption.TCP_NODELAY, true) // 允许较小的数据包的发送，降低延迟
                .handler(nettyClientHandlerInitializer)
        ;

        // 连接服务器，并异步等待成功，即启动客户端
        bootstrap.connect().addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                log.error("### [start] Fail to connect server {}:{} ###", serverHost, serverPort);
                reconnect();
                return;
            }
            channel = future.channel();
            log.info("### [start] Connect to server {}:{} success ###", serverHost, serverPort);
        });
    }

    /**
     * 重连
     */
    public void reconnect() {
        eventGroup.schedule(() -> {
            log.info("### [reconnect] Start to reconnect ###");
            start();
        }, RECONNECT_SECONDS, TimeUnit.SECONDS);
        log.info("### [reconnect] Start to reconnect after {} seconds ###", RECONNECT_SECONDS);
    }

    /**
     * 关闭 Netty Client
     */
    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.close();
        }
        eventGroup.shutdownGracefully();
    }

    /**
     * 发送消息
     *
     * @param invocation 消息体
     */
    public void send(Invocation invocation) {
        if (channel == null) {
            log.error("### [send] connection is not exist ###");
            return;
        }
        if (!channel.isActive()) {
            log.error("### [send] connection {} is not active ###", channel.id());
            return;
        }
        // 发送消息
        channel.writeAndFlush(invocation);
    }
}

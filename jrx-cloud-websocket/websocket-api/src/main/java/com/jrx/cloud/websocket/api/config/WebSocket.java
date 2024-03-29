package com.jrx.cloud.websocket.api.config;

import com.jrx.cloud.websocket.api.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author hongjc
 * @version 1.0  2020/8/21
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocket {

    @Value("${websocket.port}")
    private int port;

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    private final WebSocketHandler webSocketHandler;


    @PostConstruct
    public void start() {
        try {
            var serverBootstrap = new ServerBootstrap();
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                    .group(workerGroup, bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            ch.pipeline().addLast(webSocketHandler);
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/webSocket", null, true, 65536 * 10));
                        }
                    });

            var channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
            if (channelFuture.isSuccess()) {
                channel = channelFuture.channel();
                log.info("### WebSocket Server Has Already Started On {} ###", port);
            }
        } catch (InterruptedException e) {
            log.info("### WebSocket Server Fail To Start: {} ###", e.getMessage(), e);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            Thread.currentThread().interrupt();
        }
    }

    @PreDestroy
    public void close() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

package com.jrx.cloud.netty.server.config;

import com.jrx.cloud.netty.server.handler.NettyServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author x
 * @version 1.0  2021/6/29
 */
@Slf4j
@Component
public class NettyServer {

    @Value("${netty.port:8888}")
    private int port;

    /**
     * Netty Server Channel
     */
    private Channel channel;
    /**
     * boss 线程组，用于服务端接受客户端的连接
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * worker 线程组，用于服务端接受客户端的数据读写
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private final NettyServerHandlerInitializer nettyServerHandlerInitializer;

    public NettyServer(NettyServerHandlerInitializer nettyServerHandlerInitializer) {
        this.nettyServerHandlerInitializer = nettyServerHandlerInitializer;
    }

    /**
     * 启动 Netty Server
     */
    @PostConstruct
    public void start() {
        try {
            var serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .option(ChannelOption.SO_BACKLOG, 1024) // 服务端 accept 队列的大小
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // TCP KeepAlive 机制，实现 TCP 层级的心跳保活功能
                    .childOption(ChannelOption.TCP_NODELAY, true) // 允许较小的数据包的发送，降低延迟
                    .childHandler(nettyServerHandlerInitializer)
            ;

            var channelFuture = serverBootstrap.bind().sync();
            if (channelFuture.isSuccess()) {
                channel = channelFuture.channel();
                log.info("### [Start] Netty server has started on port {} ###", port);
            }
        } catch (InterruptedException e) {
            log.info("### [Start] Netty server fail to start: {} ###", e.getMessage(), e);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 关闭 Netty Server
     */
    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

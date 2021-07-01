package com.jrx.cloud.netty.client.controller;

import com.jrx.cloud.assembly.base.BaseRsp;
import com.jrx.cloud.netty.client.config.NettyClient;
import com.jrx.cloud.netty.common.codec.Invocation;
import com.jrx.cloud.netty.common.messgae.auth.AuthReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author x
 * @version 1.0  2021/7/1
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final NettyClient nettyClient;

    @GetMapping("/mockAuth")
    public BaseRsp<Void> mockAuth() {
        nettyClient.send(
                Invocation.builder()
                        .type(AuthReq.TYPE)
                        .message(AuthReq.builder().accessToken("TestToken").build().toString())
                        .build()
        );

        return BaseRsp.success();
    }
}

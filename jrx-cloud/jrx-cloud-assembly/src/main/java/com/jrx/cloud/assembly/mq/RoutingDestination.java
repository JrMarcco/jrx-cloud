package com.jrx.cloud.assembly.mq;

/**
 * @author hongjc
 * @version 1.0  2020/8/10
 */
public interface RoutingDestination {

    String DEFAULT_EXCHANGE = "jrx.durable.topic";

    String DEFAULT_ROUTING_KEY = "";

    String getExchange();

    String getRoutingKey();
}

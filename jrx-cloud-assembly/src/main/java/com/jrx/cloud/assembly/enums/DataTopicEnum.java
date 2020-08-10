package com.jrx.cloud.assembly.enums;

import com.jrx.cloud.assembly.helper.DelimiterHandler;
import com.jrx.cloud.assembly.mq.DirectDestination;
import com.jrx.cloud.assembly.mq.RoutingDestination;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hongjc
 * @version 1.0  2020/8/10
 */
@Getter
@RequiredArgsConstructor
public enum DataTopicEnum implements DirectDestination, RoutingDestination {
    ;

    private final String exchange;
    private final String description;
    private final Class<?> type;


    @Override
    public String getExchange() {
        return this.exchange;
    }

    @Override
    public String getDestination() {
        return DelimiterHandler.convert(this.name());
    }

    @Override
    public String getRoutingKey() {
        return this.exchange;
    }

    public String getRoutingKey(String delimiter) {
        return DelimiterHandler.convert(this.name(), delimiter);
    }
}

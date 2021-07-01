package com.jrx.cloud.netty.common.messgae;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author x
 * @version 1.0  2021/6/29
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class MessageHandlerContainer implements InitializingBean {

    /**
     * 消息类型与 MessageHandler 的映射
     */
    private final Map<String, MessageHandler> handlerMap = new HashMap<>();

    private final ApplicationContext applicationContext;

    public MessageHandlerContainer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        // 通过 ApplicationContext 获得所有 MessageHandler Bean
        applicationContext.getBeansOfType(MessageHandler.class).values()
                .forEach(messageHandler -> handlerMap.put(messageHandler.getType(), messageHandler));
        log.info("### [Init] MessageHandler count: {} ###", handlerMap.size());
    }

    /**
     * 获得类型对应的 MessageHandler
     *
     * @param type 类型
     * @return MessageHandler
     */
    public MessageHandler getMessageHandler(String type) {
        var handler = handlerMap.get(type);
        if (handler == null) {
            throw new IllegalArgumentException(String.format("### [Error] Can not get type %s message handler ###", type));
        }
        return handler;
    }

    /**
     * 获得 MessageHandler 处理的消息类
     *
     * @param handler 处理器
     * @return 消息类
     */
    public static Class<? extends Message> getMessageClass(MessageHandler handler) {
        // 获得 Bean 对应的 Class 类名。因为有可能被 AOP 代理过。
        var targetClass = AopProxyUtils.ultimateTargetClass(handler);
        // 获得接口的 Type 数组
        var interfaces = targetClass.getGenericInterfaces();
        var superclass = targetClass.getSuperclass();
        while (0 == interfaces.length && Objects.nonNull(superclass)) {
            // 以父类的接口为准
            interfaces = superclass.getGenericInterfaces();
            superclass = targetClass.getSuperclass();
        }

        // 遍历 interfaces 数组
        for (Type type : interfaces) {
            // 要求 type 是泛型参数
            if (type instanceof ParameterizedType) {
                var parameterizedType = (ParameterizedType) type;
                // 要求是 MessageHandler 接口
                if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                    var actualTypeArguments = parameterizedType.getActualTypeArguments();
                    // 取首个元素
                    if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                        // noinspection unchecked
                        return (Class<Message>) actualTypeArguments[0];
                    } else {
                        throw new IllegalStateException(String.format("### [Error] Can not get type %s message ###", handler));
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("### [Error] Can not get type %s message ###", handler));
    }
}

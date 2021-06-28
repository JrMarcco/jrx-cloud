package jrx.cloud.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


/**
 * @author x
 * @version 1.0  2021/6/28
 */
//@Configuration
public class Resilience4jConfiguration {
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> resilience4JCircuitBreakerFactoryCustomizer() {
        return resilience4JCircuitBreakerFactory -> {
            // 设置默认的配置
            resilience4JCircuitBreakerFactory.configureDefault(id -> {
                // 创建 默认 TimeLimiterConfig 对象
                TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.ofDefaults();
                // 创建 默认CircuitBreakerConfig 对象
                CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.ofDefaults();
                // 创建 Resilience4JCircuitBreakerConfiguration 对象
                return new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(timeLimiterConfig)
                        .circuitBreakerConfig(circuitBreakerConfig)
                        .build();
            });
            // 设置编号为 "slow" 的自定义配置
            resilience4JCircuitBreakerFactory.configure(resilience4JConfigBuilder -> {
                // 创建 自定义TimeLimiterConfig 对象
                TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(4))
                        .build();
                // 创建 自定义CircuitBreakerConfig 对象
                CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                        .slidingWindow(5, 5, CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                        .build();
                // 设置 Resilience4JCircuitBreakerConfiguration 对象
                resilience4JConfigBuilder
                        .timeLimiterConfig(timeLimiterConfig)
                        .circuitBreakerConfig(circuitBreakerConfig);
            }, "slow");
        };
    }
}

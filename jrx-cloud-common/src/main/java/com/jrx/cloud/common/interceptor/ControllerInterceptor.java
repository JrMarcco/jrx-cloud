package com.jrx.cloud.common.interceptor;

import com.jrx.cloud.assembly.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.jrx.cloud.assembly.error.GlobalError.SERVICE_EXCEPTION;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ControllerInterceptor {

    private final ThreadLocal<Long> idThreadLocal = new ThreadLocal<>();
    private final AtomicLong idCreator = new AtomicLong();

    @Pointcut("execution(public * com.jrx.cloud..controller.*.*(..))")
    public void matchController() {
    }

    @Around("matchController()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        idThreadLocal.set(idCreator.incrementAndGet());

        var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new BusinessException(SERVICE_EXCEPTION);
        }

        var request = requestAttributes.getRequest();
        log.info("### {} --- Request Url : [{}] ###", idThreadLocal.get(), request.getRequestURI());

        var signature = (MethodSignature) joinPoint.getSignature();
        var args = joinPoint.getArgs();

        var methodInfo = MethodInfo.from(signature.getMethod(), listMethodParam(signature), args);

        showMethodInfo(methodInfo);

        var start = System.nanoTime();
        var value = joinPoint.proceed(args);
        log.info("### {} --- [{}] 接口耗时: [{}] ms ###",
                idThreadLocal.get(),
                request.getRequestURI(),
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));

        return value;
    }


    // ====================================================================================================
    //                                   Private Method
    // ====================================================================================================
    private List<MethodParam> listMethodParam(MethodSignature signature) {
        var methodParamList = new ArrayList<MethodParam>();

        var parameters = signature.getMethod().getParameters();
        if (parameters.length > 0) {
            var parameterNames = signature.getParameterNames();
            for (int i = 0; i < parameters.length; i++) {
                methodParamList.add(MethodParam.of(i, parameterNames[i], parameters[i]));
            }
        }

        return methodParamList;
    }

    private void showMethodInfo(MethodInfo info) {
        var method = info.getMethod();
        var builder = new StringBuilder(method.getDeclaringClass().getSimpleName());
        builder.append("::").append(method.getName());

        var methodDetails = info.getDetails();
        builder.append("(");
        for (int i = 0; i < methodDetails.size(); i++) {
            var detail = methodDetails.get(i);

            builder.append(detail.getMethodParam().getName()).append(":");
            builder.append(detail.getValue());
            if (i != methodDetails.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(")");

        log.info("### {} --- {} ###", idThreadLocal.get(), builder);
    }

    // ====================================================================================================
    //                                Private Static Class
    // ====================================================================================================
    @Data(staticConstructor = "of")
    private static class MethodInfo {
        private final Method method;
        private final List<MethodDetail> details;

        private static MethodInfo from(Method method, List<MethodParam> methodParamList, Object[] args) {
            args = Optional.ofNullable(args).orElse(new Object[0]);

            var size = methodParamList.size();
            assert size == args.length;

            var details = new ArrayList<MethodDetail>();
            for (int i = 0; i < size; i++) {
                details.add(MethodDetail.of(methodParamList.get(i), args[i]));
            }

            return of(method, details);
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    private static class MethodDetail {
        private final MethodParam methodParam;
        private Object value;
    }

    @Data(staticConstructor = "of")
    private static class MethodParam {
        private final int index;
        private final String name;
        private final Parameter parameter;
    }
}

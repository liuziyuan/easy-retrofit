package io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.core;

import io.github.liuziyuan.retrofit.core.annotation.RetrofitInterceptor;
import io.github.liuziyuan.retrofit.core.annotation.RetrofitInterceptorParam;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@RetrofitInterceptor(handler = RetrofitAliyunApiGatewayInterceptor.class)
public @interface RetrofitAliyunApiGateway {

    RetrofitInterceptorParam extensions() default @RetrofitInterceptorParam();
}

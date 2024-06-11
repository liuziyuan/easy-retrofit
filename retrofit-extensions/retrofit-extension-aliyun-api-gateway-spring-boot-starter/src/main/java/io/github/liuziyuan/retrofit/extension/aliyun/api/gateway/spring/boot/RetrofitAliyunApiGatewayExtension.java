package io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.spring.boot;

import io.github.liuziyuan.retrofit.core.RetrofitInterceptorExtension;
import io.github.liuziyuan.retrofit.core.exception.RetrofitExtensionException;
import io.github.liuziyuan.retrofit.core.extension.BaseInterceptor;
import io.github.liuziyuan.retrofit.core.proxy.BaseExceptionDelegate;
import io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.core.RetrofitAliyunApiGateway;
import io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.core.RetrofitAliyunApiGatewayInterceptor;

import java.lang.annotation.Annotation;

public class RetrofitAliyunApiGatewayExtension implements RetrofitInterceptorExtension {
    @Override
    public Class<? extends Annotation> createAnnotation() {
        return RetrofitAliyunApiGateway.class;
    }

    @Override
    public Class<? extends BaseInterceptor> createInterceptor() {
        return RetrofitAliyunApiGatewayInterceptor.class;
    }

    @Override
    public Class<? extends BaseExceptionDelegate<? extends RetrofitExtensionException>> createExceptionDelegate() {
        return null;
    }
}

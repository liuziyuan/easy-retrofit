package io.github.liuziyuan.retrofit.spring.boot.aliyun.api.gateway;

import io.github.liuziyuan.retrofit.core.builder.BaseCallAdapterFactoryBuilder;
import retrofit2.CallAdapter;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class RxJava3CallAdapterFactoryBuilder extends BaseCallAdapterFactoryBuilder {
    @Override
    public CallAdapter.Factory buildCallAdapterFactory() {
        return RxJava3CallAdapterFactory.create();
    }
}

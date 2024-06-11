package io.github.liuziyuan.retrofit.spring.boot.aliyun.api.gateway;

import io.github.liuziyuan.retrofit.core.builder.BaseCallAdapterFactoryBuilder;
import org.springframework.stereotype.Component;
import retrofit2.CallAdapter;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;

/**
 * @author liuziyuan
 */
@Component
public class GuavaCallAdapterFactoryBuilder extends BaseCallAdapterFactoryBuilder {

    @Override
    public CallAdapter.Factory buildCallAdapterFactory() {
        return GuavaCallAdapterFactory.create();
    }
}

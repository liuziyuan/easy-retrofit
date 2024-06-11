package io.github.liuziyuan.retrofit.spring.boot.aliyun.api.gateway;

import io.github.liuziyuan.retrofit.core.builder.BaseConverterFactoryBuilder;
import org.springframework.stereotype.Component;
import retrofit2.Converter;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author liuziyuan
 */
public class JacksonConvertFactoryBuilder extends BaseConverterFactoryBuilder {

    @Override
    public Converter.Factory buildConverterFactory() {
        return JacksonConverterFactory.create();
    }
}

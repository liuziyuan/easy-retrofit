package io.github.liuziyuan.retrofit.spring.boot.aliyun.api.gateway;

import io.github.liuziyuan.retrofit.core.RetrofitBuilderExtension;
import io.github.liuziyuan.retrofit.core.builder.*;

import java.util.ArrayList;

public class RetrofitAliyunApiGatewayBuilderExtension implements RetrofitBuilderExtension {

    @Override
    public boolean enable() {
        return true;
    }

    @Override
    public String globalBaseUrl() {
        return null;
    }

    @Override
    public Class<? extends BaseCallAdapterFactoryBuilder>[] globalCallAdapterFactoryBuilderClazz() {
        return new ArrayList<Class<? extends BaseCallAdapterFactoryBuilder>>() {{
            add(BodyCallAdapterFactoryBuilder.class);
            add(GuavaCallAdapterFactoryBuilder.class);
            add(RxJava3CallAdapterFactoryBuilder.class);
        }}.toArray(new Class[0]);
    }

    @Override
    public Class<? extends BaseConverterFactoryBuilder>[] globalConverterFactoryBuilderClazz() {
        return new ArrayList<Class<? extends BaseConverterFactoryBuilder>>() {{
            add(JacksonConvertFactoryBuilder.class);
        }}.toArray(new Class[0]);
    }

    @Override
    public Class<? extends BaseOkHttpClientBuilder> globalOkHttpClientBuilderClazz() {
        return OkHttpClientBuilder.class;
    }

    @Override
    public Class<? extends BaseCallBackExecutorBuilder> globalCallBackExecutorBuilderClazz() {
        return null;
    }

    @Override
    public Class<? extends BaseCallFactoryBuilder> globalCallFactoryBuilderClazz() {
        return null;
    }

    @Override
    public String globalValidateEagerly() {
        return null;
    }
}

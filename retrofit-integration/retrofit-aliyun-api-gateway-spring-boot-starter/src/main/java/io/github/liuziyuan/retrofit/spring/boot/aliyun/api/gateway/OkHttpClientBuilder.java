package io.github.liuziyuan.retrofit.spring.boot.aliyun.api.gateway;

import com.alibaba.cloudapi.sdk.exception.SdkException;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;
import io.github.liuziyuan.retrofit.core.builder.BaseOkHttpClientBuilder;
import io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.spring.boot.OkHttpConfig;
import io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.spring.boot.RetrofitAliyunApiGatewayProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OkHttpClientBuilder extends BaseOkHttpClientBuilder {

    private final ApplicationContext applicationContext;
    private RetrofitAliyunApiGatewayOkHttpProperties properties;
    private RetrofitAliyunApiGatewayProperties retrofitAliyunApiGatewayProperties;

    public OkHttpClientBuilder(RetrofitAliyunApiGatewayOkHttpProperties aliyunApiGatewayProperties, RetrofitAliyunApiGatewayProperties retrofitAliyunApiGatewayProperties, ApplicationContext applicationContext) {
        this.properties = aliyunApiGatewayProperties;
        this.applicationContext = applicationContext;
        this.retrofitAliyunApiGatewayProperties = retrofitAliyunApiGatewayProperties;
    }

    @Override
    public OkHttpClient.Builder buildOkHttpClientBuilder(OkHttpClient.Builder builder) {
        if (null == properties || null == retrofitAliyunApiGatewayProperties) {
            throw new SdkException("buildParam must not be null");
        }
        HttpClientBuilderParams builderParam = new HttpClientBuilderParams();
        builderParam.setHost(retrofitAliyunApiGatewayProperties.getHost());
        builderParam.setAppKey(retrofitAliyunApiGatewayProperties.getAppKey());
        builderParam.setHost(retrofitAliyunApiGatewayProperties.getHost());
        builderParam.setScheme(retrofitAliyunApiGatewayProperties.getScheme());
        builderParam.check();

        Dispatcher dispatcher = new Dispatcher();
        if (properties.getOkHttpConfigClazz() != null) {
            try {
                OkHttpConfig bean = applicationContext.getBean(properties.getOkHttpConfigClazz());
                if (bean.sslSocketFactory() != null && bean.x509TrustManager() != null) {
                    builder.sslSocketFactory(bean.sslSocketFactory(), bean.x509TrustManager());
                }
                if (bean.hostnameVerifier() != null) {
                    builder.hostnameVerifier(bean.hostnameVerifier());
                }
                if (bean.executorService() != null) {
                    dispatcher = new Dispatcher(bean.executorService());
                }
                if (bean.idleCallback() != null) {
                    dispatcher.setIdleCallback(bean.idleCallback());
                }
                dispatcher.setMaxRequests(properties.getDispatchMaxRequests());
                dispatcher.setMaxRequestsPerHost(properties.getDispatchMaxRequestsPerHost());

            } catch (BeansException e) {
                log.warn("okHttpConfigClazz is not found");
            }

        }

        ConnectionPool connectionPool = new ConnectionPool(properties.getMaxIdleConnections(), properties.getMaxIdleTimeMillis(), TimeUnit.MILLISECONDS);

        return builder
                .connectionPool(connectionPool).dispatcher(dispatcher)
                .readTimeout(properties.getReadTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(properties.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .connectTimeout(properties.getConnectionTimeout(), TimeUnit.MILLISECONDS);
    }

}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OkHttpClientBuilder extends BaseOkHttpClientBuilder implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Autowired
    private RetrofitAliyunApiGatewayProperties aliyunApiGatewayProperties;


    @Override
    public OkHttpClient.Builder buildOkHttpClientBuilder(OkHttpClient.Builder builder) {
        if (null == aliyunApiGatewayProperties) {
            throw new SdkException("buildParam must not be null");
        }
        HttpClientBuilderParams builderParam = new HttpClientBuilderParams();
        builderParam.setHost(aliyunApiGatewayProperties.getHost());
        builderParam.setAppKey(aliyunApiGatewayProperties.getAppKey());
        builderParam.setHost(aliyunApiGatewayProperties.getHost());
        builderParam.setScheme(aliyunApiGatewayProperties.getScheme());

        builderParam.check();
        Dispatcher dispatcher = new Dispatcher();
        if (aliyunApiGatewayProperties.getOkHttpConfigClazz() != null) {
            try {
                OkHttpConfig bean = applicationContext.getBean(aliyunApiGatewayProperties.getOkHttpConfigClazz());
                if (bean != null && bean.sslSocketFactory() != null && bean.x509TrustManager() != null) {
                    builder.sslSocketFactory(bean.sslSocketFactory(), bean.x509TrustManager());
                }
                if (bean != null && bean.hostnameVerifier() != null) {
                    builder.hostnameVerifier(bean.hostnameVerifier());
                }
                if (bean != null && bean.executorService() != null) {
                    dispatcher = new Dispatcher(bean.executorService());
                }
                if (bean != null && bean.idleCallback() != null) {
                    dispatcher.setIdleCallback(bean.idleCallback());
                }
                dispatcher.setMaxRequests(aliyunApiGatewayProperties.getDispatchMaxRequests());
                dispatcher.setMaxRequestsPerHost(aliyunApiGatewayProperties.getDispatchMaxRequestsPerHost());

            } catch (BeansException e) {
                log.warn("okHttpConfigClazz is not found");
            }

        }

        ConnectionPool connectionPool = new ConnectionPool(aliyunApiGatewayProperties.getMaxIdleConnections(), aliyunApiGatewayProperties.getMaxIdleTimeMillis(), TimeUnit.MILLISECONDS);

        return builder
                .connectionPool(connectionPool).dispatcher(dispatcher)
                .readTimeout(aliyunApiGatewayProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(aliyunApiGatewayProperties.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .connectTimeout(aliyunApiGatewayProperties.getConnectionTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

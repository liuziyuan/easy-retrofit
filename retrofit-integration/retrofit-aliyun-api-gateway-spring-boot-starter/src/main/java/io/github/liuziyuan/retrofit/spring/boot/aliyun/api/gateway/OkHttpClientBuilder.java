package io.github.liuziyuan.retrofit.spring.boot.aliyun.api.gateway;

import io.github.liuziyuan.retrofit.core.builder.BaseOkHttpClientBuilder;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.util.concurrent.TimeUnit;

@Component
public class OkHttpClientBuilder extends BaseOkHttpClientBuilder {

    @Autowired
    private X509TrustManager x509TrustManager;

    @Autowired
    private SSLSocketFactory sslSocketFactory;

    @Autowired
    private ConnectionPool pool;

    @Override
    public OkHttpClient.Builder buildOkHttpClientBuilder(OkHttpClient.Builder builder) {
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, x509TrustManager)
                .retryOnConnectionFailure(false)
                .connectionPool(pool)
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
    }
}

package io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.spring.boot;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.util.concurrent.ExecutorService;

public abstract class OkHttpConfig {

    public abstract X509TrustManager x509TrustManager();

    public abstract SSLSocketFactory sslSocketFactory();

    public abstract HostnameVerifier hostnameVerifier();

    public abstract ExecutorService executorService();

    public abstract Runnable idleCallback();
}

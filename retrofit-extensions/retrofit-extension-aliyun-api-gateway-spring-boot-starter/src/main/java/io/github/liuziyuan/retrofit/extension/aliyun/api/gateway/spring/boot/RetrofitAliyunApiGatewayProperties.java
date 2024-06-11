package io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.spring.boot;

import com.alibaba.cloudapi.sdk.enums.Scheme;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "retrofit.aliyun.api-gateway")
public class RetrofitAliyunApiGatewayProperties {

    private String appKey;
    private String appSecret;
    private Scheme scheme;
    private String host;
    long connectionTimeout = 10000l;
    long readTimeout = 10000l;
    long writeTimeout = 10000l;

    private Class<? extends OkHttpConfig> okHttpConfigClazz;


    /**
     * connectionPool
     **/
    private int maxIdleConnections = 5;
    private long maxIdleTimeMillis = 10 * 1000L;
    private long keepAliveDurationMillis = -1L;



    /**
     * dispatcher
     **/
    private int dispatchMaxRequests = 64;
    private int dispatchMaxRequestsPerHost = 5;
}

package io.github.liuziyuan.retrofit.spring.boot.aliyun.api.gateway;

import io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.spring.boot.OkHttpConfig;
import io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.spring.boot.RetrofitAliyunApiGatewayProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "retrofit.aliyun.api-gateway.okhttp")
public class RetrofitAliyunApiGatewayOkHttpProperties {

    public RetrofitAliyunApiGatewayOkHttpProperties() {
        super();
    }

    private long connectionTimeout = 10000l;
    private long readTimeout = 10000l;
    private long writeTimeout = 10000l;

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

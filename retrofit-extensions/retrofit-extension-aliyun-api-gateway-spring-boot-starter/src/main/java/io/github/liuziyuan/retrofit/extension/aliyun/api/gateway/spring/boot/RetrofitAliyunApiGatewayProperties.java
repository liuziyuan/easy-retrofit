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
}

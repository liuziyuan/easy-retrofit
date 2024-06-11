package io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.spring.boot;

import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;
import io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.core.RetrofitAliyunApiGatewayInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RetrofitAliyunApiGatewayProperties.class})
public class RetrofitAliyunApiGatewayConfig {

    @Bean
    @ConditionalOnMissingBean
    public RetrofitAliyunApiGatewayInterceptor retrofitAliyunApiGatewayInterceptor(@Autowired RetrofitAliyunApiGatewayProperties aliyunApiGatewayProperties) {
        HttpClientBuilderParams httpClientBuilderParams = new HttpClientBuilderParams();
        httpClientBuilderParams.setHost(aliyunApiGatewayProperties.getHost());
        httpClientBuilderParams.setScheme(aliyunApiGatewayProperties.getScheme());
        httpClientBuilderParams.setAppKey(aliyunApiGatewayProperties.getAppKey());
        httpClientBuilderParams.setAppSecret(aliyunApiGatewayProperties.getAppSecret());
        return new RetrofitAliyunApiGatewayInterceptor(httpClientBuilderParams);
    }
}

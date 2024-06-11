package io.github.liuziyuan.retrofit.spring.boot.aliyun.api.gateway;

import io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.spring.boot.RetrofitAliyunApiGatewayProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RetrofitAliyunApiGatewayOkHttpProperties.class, RetrofitAliyunApiGatewayProperties.class})
public class RetrofitSpringBootAliyunApiGatewayConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClientBuilder okHttpClientBuilder(@Autowired RetrofitAliyunApiGatewayOkHttpProperties retrofitAliyunApiGatewayOkHttpProperties,
                                                   @Autowired RetrofitAliyunApiGatewayProperties retrofitAliyunApiGatewayProperties) {
        return new OkHttpClientBuilder(retrofitAliyunApiGatewayOkHttpProperties, retrofitAliyunApiGatewayProperties, applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

package io.github.liuziyuan.retrofit.extension.sentinel.spring.boot;

import io.github.liuziyuan.retrofit.core.RetrofitResourceContext;
import io.github.liuziyuan.retrofit.extension.sentinel.core.RetrofitSentinelPropertiesProcessor;
import io.github.liuziyuan.retrofit.extension.sentinel.core.interceptor.SentinelBlockException;
import io.github.liuziyuan.retrofit.extension.sentinel.core.interceptor.SentinelBlockExceptionFallBackHandler;
import io.github.liuziyuan.retrofit.extension.sentinel.spring.boot.config.RetrofitSpringSentinelDegradeRuleProperties;
import io.github.liuziyuan.retrofit.extension.sentinel.spring.boot.config.RetrofitSpringSentinelFlowRuleProperties;
import io.github.liuziyuan.retrofit.spring.boot.SpringCDIBeanManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RetrofitSpringSentinelFlowRuleProperties.class, RetrofitSpringSentinelDegradeRuleProperties.class})
public class RetrofitSentinelSpringBootConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 注册接口扩展
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RetrofitSentinelInterceptorExtension retrofitSentinelInterceptorExtension() {
        return new RetrofitSentinelInterceptorExtension();
    }

    @Bean
    @ConditionalOnMissingBean
    public SentinelBlockExceptionFallBackHandler exceptionFallBackHandler() {
        return new SentinelBlockExceptionFallBackHandler(SentinelBlockException.class, new SpringCDIBeanManager(applicationContext));
    }

    @Bean
    @ConditionalOnMissingBean
    public RetrofitSentinelPropertiesProcessor retrofitSentinelPropertiesProcessor(
            @Autowired RetrofitSpringSentinelDegradeRuleProperties degradeRuleProperties,
            @Autowired RetrofitSpringSentinelFlowRuleProperties flowRuleProperties) {
        return new RetrofitSentinelPropertiesProcessor(applicationContext.getBean(RetrofitResourceContext.class), new SpringCDIBeanManager(applicationContext), degradeRuleProperties, flowRuleProperties);
    }

}

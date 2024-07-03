package com.santanu.LoadBalancer.config;

import com.santanu.LoadBalancer.requestHandler.RequestForwardFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This class register the custom filter(RequestForwardFilter) in the spring bean
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestForwardFilter> loggingFilter(){
        FilterRegistrationBean<RequestForwardFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestForwardFilter());
        registrationBean.addUrlPatterns("/lb/*");

        return registrationBean;
    }
}

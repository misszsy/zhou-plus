package com.zhou.plus.web.config;


import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhou.plus.framework.utils.SpringContextHolder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;

@Configuration
@ComponentScan(basePackages = {"com.zhou.plus.busi"})
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }


    /**
     * 配置fastJson
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter=new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm");
        fastJsonConfig.setFeatures(Feature.DisableCircularReferenceDetect);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty,
                                             SerializerFeature.WriteNullStringAsEmpty,
                                             SerializerFeature.WriteDateUseDateFormat
                                                );
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter);
    }

    /**
     * 验证器
     * @return
     */
    @Bean("validator")
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        return new LocalValidatorFactoryBean();
    }


    @Bean
    @Lazy(false)
    public SpringContextHolder springContextHolder() {
             return new SpringContextHolder();
        }


    @Bean
    public FilterRegistrationBean urlRewrite(MyUrlRewriteFilter urlRewriteFilter) {
        FilterRegistrationBean registration =
                new FilterRegistrationBean<>(urlRewriteFilter);
        registration.setUrlPatterns(Collections.singletonList("/*"));
        Map initParam = new HashMap<>();
        initParam.put("confPath", "urlrewirte.xml");
        initParam.put("infoLevel", "INFO");
        registration.setInitParameters(initParam);
        return registration;
    }
}

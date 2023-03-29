package com.wyx.springboot.config;

import com.wyx.springboot.config.Interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor())
//                .addPathPatterns("/**") // 拦截所有请求，通过判断是否有 token是否合法来决定是否需要登录
//                .excludePathPatterns("/user/login","/user/register","/**/export", "/**/index.html#","/**/import","/**/swagger-ui","/**/swagger-ui/**","/file/**");
   }
    @Bean //把JwtInterceptor 注入到SpringBoot里
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

}

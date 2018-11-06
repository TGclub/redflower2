package com.test.redflower2.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorWebConfigurer implements WebMvcConfigurer {
    private AuthenticationInterceptor authenticationInterceptor;

    public InterceptorWebConfigurer(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    /**
     * 配置跨源请求处理。
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .exposedHeaders("authorization")
                .allowCredentials(false).maxAge(3600);//不需要证书
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = new ArrayList<>();
        //添加不过滤的路径
        excludePaths.add("/user/login");
        excludePaths.add("/error");
        excludePaths.add("/network/test");//networkController test 通过
        excludePaths.add("/user/test");//userController test通过
        excludePaths.add("/swagger-ui.html");
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")//用于添加拦截规则
                .excludePathPatterns(excludePaths);

    }
}

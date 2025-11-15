package com.rabbiter.dm.config;

import com.rabbiter.dm.interceptor.LoginInterceptor;
import com.rabbiter.dm.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author rabbiter
 * @date 2021-12-08
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {
    private final SecurityInterceptor securityInterceptor;
    private final LoginInterceptor loginInterceptor;

    // 直接注入配置值
    @Value("${constant.upload-path}")
    private String uploadPath;

    public MyConfig(SecurityInterceptor securityInterceptor, LoginInterceptor loginInterceptor) {
        this.securityInterceptor = securityInterceptor;
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截器
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/imgs/**", "/js/**", "/css/**", "/login/**"
                        , "/login.html", "/error", "/upload_img/**","/permission/**").order(0);

        //权限拦截器
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/imgs/**", "/js/**", "/css/**", "/login/**"
                        , "/login.html", "/error", "/upload_img/**","/permission/**").order(1);
    }

    /**
     * 跨域
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(false);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

    /**
     * 外部资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加日志输出，便于调试
        System.out.println("配置静态资源映射，uploadPath: " + uploadPath);

        // 使用直接注入的 uploadPath
        registry.addResourceHandler("/upload_img/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
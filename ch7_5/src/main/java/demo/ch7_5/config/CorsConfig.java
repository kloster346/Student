package demo.ch7_5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("enable config");
        registry.addMapping("/**")//匹配所有路径
                .allowedOriginPatterns(("*"))//允许源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")//允许的HTTP方法
                .allowCredentials(true)//是否允许携带cookie
                .maxAge(3600);//预检请求缓存时间（秒）
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}

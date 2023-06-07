package cike.chatgpt.config

import cike.chatgpt.interceptor.RequiredLoginInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig implements WebMvcConfigurer {

  @Autowired
  private RequiredLoginInterceptor requiredLoginInterceptor;

  @Override
  void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")  // 拦截所有的请求
        .allowedOriginPatterns("*")
        .allowCredentials(true)
        .allowedMethods("*")   // 允许跨域的方法，可以单独配置
        .allowedHeaders("*");  // 允许跨域的请求头，可以单独配置
  }

  @Override
  void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requiredLoginInterceptor).addPathPatterns("/**", "/**/*");
  }
}

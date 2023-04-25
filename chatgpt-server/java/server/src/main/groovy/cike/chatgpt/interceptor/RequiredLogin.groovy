package cike.chatgpt.interceptor

import cike.chatgpt.SessionManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.METHOD, ElementType.TYPE])
@interface RequiredLogin {}

@Component
class RequiredLoginInterceptor implements HandlerInterceptor {

    static Logger log = LoggerFactory.getLogger(HandlerInterceptor.class)

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler

            // 判断是否需要登录
            RequiredLogin requiredLogin = handlerMethod.getMethod().getDeclaredAnnotation(RequiredLogin.class)

            if (null == requiredLogin) {
                requiredLogin = handlerMethod.getBeanType().getAnnotation(RequiredLogin.class)
            }

            if (null == requiredLogin) {
                return true
            }

            if (null != requiredLogin) {
                String authorization = request.getHeader("Authorization");
                if (authorization && SessionManager.get(authorization.substring(7))) {
                    return true
                }
                response.sendError(401);
                return false;
            }
        }
        return true;
    }
}

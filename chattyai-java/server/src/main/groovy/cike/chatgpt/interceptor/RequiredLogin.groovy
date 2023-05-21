package cike.chatgpt.interceptor

import cike.chatgpt.SessionManager
import cike.chatgpt.repository.enums.UserStatusEnum
import cike.chatgpt.repository.rbac.RABCRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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
@interface RequiredLogin {
  Permission permission() default Permission.IGNORE
}

@Component
class RequiredLoginInterceptor implements HandlerInterceptor {

  static Logger log = LoggerFactory.getLogger(HandlerInterceptor.class)

  @Autowired
  SessionManager sessionManager

  @Override
  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler

    // 判断是否需要登录
    RequiredLogin requiredLogin = handlerMethod.getMethod().getDeclaredAnnotation(RequiredLogin.class)

    if (null == requiredLogin) {
      requiredLogin = handlerMethod.getBeanType().getAnnotation(RequiredLogin.class)
    }

    if (null == requiredLogin) {
      return true
    }

    String authorization = request.getHeader("Authorization")
    if (!authorization) {
      response.sendError(401)
      return false
    }

    def currentUser = sessionManager.get(authorization.substring(7))

    if (!currentUser) {
      response.sendError(401)
      return false
    }

    if (currentUser.status != UserStatusEnum.NORMAL.code) {
      response.sendError(401)
      return false
    }

    if (currentUser.expiredTime && currentUser.expiredTime.before(new Date())) {
      response.sendError(401)
      return false
    }

    if (requiredLogin.permission() && requiredLogin.permission() != Permission.IGNORE) {
      return RABCRepository.getUserPermission(currentUser.uid).contains(requiredLogin.permission().getCode())
    }
    return true
  }
}

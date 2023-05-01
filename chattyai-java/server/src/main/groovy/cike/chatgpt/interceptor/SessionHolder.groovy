package cike.chatgpt.interceptor

import cike.chatgpt.SessionManager
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest

@Component
@Slf4j
@Scope("prototype")
class SessionHolder {

  @Autowired
  HttpServletRequest request;

  @Autowired
  private SessionManager sessionManager

  String getCurrentUserUID() {

    String authorization = request.getHeader("Authorization")

    if (!authorization) {
      return null
    }

    def u = sessionManager.get(authorization.substring(7))
    return u.uid
  }
}

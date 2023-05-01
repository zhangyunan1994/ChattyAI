package cike.chatgpt.service


import cike.chatgpt.SessionManager
import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.repository.AuthSessionTokenRepository
import cike.chatgpt.repository.UserRepository
import cike.chatgpt.repository.entity.User
import cike.chatgpt.utils.NanoIdUtils
import com.google.common.base.Preconditions
import com.google.common.base.Strings
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.LocalDateTime

@Service
class AuthService {

  static Logger log = LoggerFactory.getLogger(AuthService.class)

  @Autowired
  private UserRepository userRepository

  @Autowired
  private SessionManager sessionManager

  CommonResponse<String> loginByPassword(String username, String password, String verifyCode) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(username), "用户名不能为空");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "密码不能为空");

    User user = userRepository.findByUsername(username);
    if (null == user) {
      return new CommonResponse(status: CommonResponse.Fail, message: "Failed to find user")
    }

    if (user.passwordHash != password) {
      return new CommonResponse(status: CommonResponse.Fail, message: "Password mismatch")
    }

    def token = NanoIdUtils.randomNanoId(50)

    AuthSessionTokenRepository.addRecord(token, user.uid, LocalDateTime.now().plusDays(3))
    sessionManager.put(token.toString(), user)

    log.info("用户登录 token {}", user.uid)
    return new CommonResponse(status: CommonResponse.Success, data: token)
  }

  CommonResponse<UserDTO> checkToken(String token) {
    def user = sessionManager.get(token)
    if (user) {
      return new CommonResponse(status: CommonResponse.Success, data: new UserDTO(nickname: user.nickname, avatar: user.avatar, description: user.description))
    } else {
      return new CommonResponse(status: CommonResponse.Fail, message: "Token Expired")
    }
  }
}

class UserDTO {
  String nickname
  String avatar
  String description
}
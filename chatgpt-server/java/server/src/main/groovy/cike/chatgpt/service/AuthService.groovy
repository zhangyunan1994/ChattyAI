package cike.chatgpt.service


import cike.chatgpt.SessionManager
import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.repository.AuthSessionTokenRepository
import cike.chatgpt.repository.User
import cike.chatgpt.repository.UserRepository
import cike.chatgpt.utils.NanoIdUtils
import com.google.common.base.Preconditions
import com.google.common.base.Strings
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

import java.time.LocalDateTime

@Service
class AuthService {

    static Logger log = LoggerFactory.getLogger(AuthService.class)

    CommonResponse<String> loginByPassword(String username, String password, String verifyCode) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(username), "用户名不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "密码不能为空");

        User user = UserRepository.findByUsername(username);
        if (null == user) {
            return new CommonResponse(status: CommonResponse.Fail, message: "Failed to find user")
        }

        if (user.password != password) {
            return new CommonResponse(status: CommonResponse.Fail, message: "Password mismatch")
        }

        def token = NanoIdUtils.randomNanoId(50)

        AuthSessionTokenRepository.addRecord(token, user.uid, LocalDateTime.now().plusDays(3))
        SessionManager.put(token.toString(), user)

        log.info("用户登录 token {}", user.uid)
        return new CommonResponse(status: CommonResponse.Success, data: token)
    }

    CommonResponse<User> checkToken(String token) {
        def user = SessionManager.get(token)
        if (user) {
            return new CommonResponse(status: CommonResponse.Success, data: user)
        } else {
            return new CommonResponse(status: CommonResponse.Fail, message: "Token Expired")
        }
    }
}

class AuthSuccessResponse {

    String status;
    String message;
    String token;
    UserDTO userDTO;

}

class UserDTO {

    String realName;
    String username;
}
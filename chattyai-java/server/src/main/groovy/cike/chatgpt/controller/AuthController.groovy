package cike.chatgpt.controller

import cike.chatgpt.interceptor.RequiredLogin
import cike.chatgpt.service.AuthService
import cike.chatgpt.service.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@RequestMapping("auth")
class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("signup")
  CommonResponse<String> signup(@RequestBody AuthParam loginParam) {
    try {
      authService.signup(loginParam);
    } catch (IllegalArgumentException e) {
      CommonResponse<String>.failure(e.message)
    }
  }

  @PostMapping("/login/withPassword")
  CommonResponse<String> loginByPassword(@RequestBody AuthParam loginParam) {
    authService.loginByPassword(loginParam.username, loginParam.password, loginParam.verifyCode);
  }

  @GetMapping("checkToken")
  @RequiredLogin
  CommonResponse<UserDTO> checkToken(@RequestHeader("Authorization") String authorization) {
    if (authorization && authorization.startsWith("Bearer ")) {
      return authService.checkToken(authorization.substring(7))
    }
    return new CommonResponse<UserDTO>(status: CommonResponse.Fail, message: "No Authorization")
  }
}


class AuthParam {
  String username;
  String verifyCode;
  String password;
  String email
  String invitationCode
}

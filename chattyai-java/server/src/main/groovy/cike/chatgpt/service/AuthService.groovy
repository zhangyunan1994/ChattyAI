package cike.chatgpt.service

import cike.chatgpt.SessionManager
import cike.chatgpt.controller.AuthParam
import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.repository.AuthSessionTokenRepository
import cike.chatgpt.repository.UserRepository
import cike.chatgpt.repository.entity.User
import cike.chatgpt.repository.enums.UserRoleEnum
import cike.chatgpt.repository.enums.UserStatusEnum
import cike.chatgpt.repository.enums.WalletInfoRecordTypeEnum
import cike.chatgpt.utils.NanoIdUtils
import com.google.common.base.Preconditions
import com.google.common.base.Strings
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class AuthService {

  static Logger log = LoggerFactory.getLogger(AuthService.class)

  @Autowired
  private UserRepository userRepository

  @Autowired
  private SessionManager sessionManager

  @Autowired
  private MemberService memberService

  @Autowired
  private MemberWalletService memberWalletService

  CommonResponse<String> loginByPassword(String username, String password, String verifyCode) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(username), "用户名不能为空");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "密码不能为空");

    User user = userRepository.findByUsername(username);
    if (null == user) {
      return new CommonResponse(status: CommonResponse.Fail, message: "Failed to find user")
    }

    if (user.status != UserStatusEnum.NORMAL.code) {
      return new CommonResponse(status: CommonResponse.Fail, message: "用户状态异常，请联系管理员")
    }

    if (user.expiredTime && user.expiredTime.before(new Date())) {
      return new CommonResponse(status: CommonResponse.Fail, message: "用户状态异常，请联系管理员")
    }

    if (user.passwordHash != password) {
      return new CommonResponse(status: CommonResponse.Fail, message: "Password mismatch")
    }

    def token = NanoIdUtils.randomNanoId(50)

    AuthSessionTokenRepository.addRecord(token, user.uid, LocalDateTime.now().plusDays(7))
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

  @Transactional
  CommonResponse<String> signup(AuthParam authParam) {
    User user = userRepository.findByUsername(authParam.username);
    if (user != null) {
      return CommonResponse<String>.failure("用户已经存在, 请更换用户名或者登录")
    }

    user = userRepository.findByEmail(authParam.email);
    if (user != null) {
      return CommonResponse<String>.failure("用户已经存在, 请更换邮箱或者登录")
    }
    def invitationCodeUser = null
    if (authParam.invitationCode) {
      invitationCodeUser = userRepository.findUserByInvitationCode(authParam.invitationCode)
      if (!invitationCodeUser) {
        return CommonResponse<String>.failure("邀请码不存在，请仔细核对或者清空")
      }
    }

    User memberInfo = new User()
    memberInfo.setUsername(authParam.username)
    memberInfo.setNickname("可爱的你")
    memberInfo.setPasswordHash(authParam.password)
    memberInfo.setEmail(authParam.email)
    memberInfo.setAvatar("https://img.syt5.com/2020/1209/20201209013502145.jpg.420.420.jpg")
    memberInfo.setStatus((byte) 1)
    LocalDateTime expiredTime = LocalDateTime.now().plusYears(3);
    Instant instant = expiredTime.atZone(ZoneId.systemDefault()).toInstant();
    memberInfo.setExpiredTime(Date.from(instant))
    memberInfo.setRole(UserRoleEnum.USER.code)
    memberInfo.setInvitationFrom(authParam.invitationCode)
    String uid = memberService.addUser(memberInfo)

    memberWalletService.chattyAIRegisterToken(uid, 10_0000)
    if (invitationCodeUser) {
      memberWalletService.chattyAITokenAdd(invitationCodeUser.uid, 66666, WalletInfoRecordTypeEnum.INVITATION_CODE)
    }

    def token = NanoIdUtils.randomNanoId(50)

    AuthSessionTokenRepository.addRecord(token, uid, LocalDateTime.now().plusDays(7))
    sessionManager.put(token.toString(), memberInfo)

    log.info("用户注册 token {}", uid)
    return new CommonResponse(status: CommonResponse.Success, data: token)
  }
}

class UserDTO {
  String nickname
  String avatar
  String description
}
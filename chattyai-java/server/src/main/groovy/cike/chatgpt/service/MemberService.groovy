package cike.chatgpt.service

import cike.chatgpt.controller.*
import cike.chatgpt.repository.AuthSessionTokenRepository
import cike.chatgpt.repository.UserRepository
import cike.chatgpt.repository.entity.User
import cike.chatgpt.repository.enums.UserRoleEnum
import cike.chatgpt.repository.enums.UserStatusEnum
import cike.chatgpt.repository.rbac.RABCRepository
import cike.chatgpt.utils.CollectionUtil
import cike.chatgpt.utils.NanoIdUtils
import cike.chatgpt.utils.RandomStringUtil
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.google.common.base.Preconditions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class MemberService {

  @Autowired
  private UserRepository userRepository

  @Autowired
  private AuthSessionTokenRepository authSessionTokenRepository

  @Autowired
  private RABCRepository rabcRepository

  PageList<User> pageList(int currentPage, int pageSize, String searchText,
                          String startTime,
                          String endTime) {
    Page<Object> page = PageHelper.startPage(currentPage, pageSize);
    List<User> result = userRepository.findByCondition(searchText, startTime, endTime)
    PageList<User>.of(currentPage, pageSize, page.getTotal(), result)
  }

  String addUser(User user) {
    Preconditions.checkArgument(user.username != null && user.username.length() >= 8 && user.username.length() <= 16, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(user.nickname != null && user.nickname.length() >= 2 && user.nickname.length() <= 8, "昵称错误，至少 2 位")
    Preconditions.checkArgument(user.passwordHash != null && user.passwordHash.length() >= 8 && user.passwordHash.length() <= 16, "密码错误，至少 8 位")
    Preconditions.checkArgument(user.avatar != null && user.avatar.length() > 8 && user.avatar.startsWith("http"), "头像错误，至少 8 位且以 http 开头")
    Preconditions.checkArgument(user.status != null && (user.status == 1 || user.status == 2), "状态错误")
    Preconditions.checkArgument(user.expiredTime != null, "过期时间不能为空")
    Preconditions.checkArgument(user.role != null, "用户角色不能为空")
    def roleEnum = UserRoleEnum.getByCode(user.role)
    Preconditions.checkArgument(roleEnum != null, "用户角色错误" + user.role)
    Preconditions.checkArgument(userRepository.findByUsername(user.username) == null, "用户名已存在，不能添加")

    // 生成一个邀请码
    def invitationCode = RandomStringUtil.randomFromNumberAndUpLetter(10)
    def existsUser = userRepository.findUserByInvitationCode(invitationCode)
    while (existsUser) {
      invitationCode = RandomStringUtil.randomFromNumberAndUpLetter(10)
      existsUser = userRepository.findUserByInvitationCode(invitationCode)
    }

    user.setInvitationCode(invitationCode)
    user.setInvitationFrom(user.invitationCode)
    user.setId(null)
    user.setUid(NanoIdUtils.randomNanoId())
    userRepository.addUser(user)
    rabcRepository.setUserRole(user.getUid(), roleEnum)
    return user.uid
  }

  void modifyUserWithoutPassword(User param) {
    Preconditions.checkArgument(param.id != null && param.id >= 0, "呦呦呦")

    def existsUser = userRepository.findById(param.getId())
    Preconditions.checkArgument(existsUser != null, "登录用户名错误，至少 8 位")

    Preconditions.checkArgument(param.username != null && param.username.length() >= 8, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(param.nickname != null && param.nickname.length() >= 2, "昵称错误，至少 2 位")
    Preconditions.checkArgument(param.avatar != null && param.avatar.length() > 8 && param.avatar.startsWith("http"), "头像错误，至少 8 位且以 http 开头")
    Preconditions.checkArgument(param.status == 1 || param.status == 2 || param.status == 3, "状态错误")
    Preconditions.checkArgument(param.expiredTime != null, "过期时间不能为空")
    Preconditions.checkArgument(param.role != null, "用户角色不能为空")
    def roleEnum = UserRoleEnum.getByCode(param.role)
    Preconditions.checkArgument(roleEnum != null, "用户角色错误" + param.role)

    def existsSameUsernameUser = userRepository.findByUsername(param.username)
    if (existsSameUsernameUser != null && existsSameUsernameUser.getId() != param.id) {
      throw new IllegalArgumentException("用户名已经存在，不能修改")
    }

    param.setCreateTime(null)
    param.setUpdateTime(new Date())
    param.setPasswordHash(null)
    param.setInvitationCode(null)

    userRepository.modifyUser(param)
    rabcRepository.setUserRole(existsUser.getUid(), roleEnum)

    if (param.status != UserStatusEnum.NORMAL.code) {
      authSessionTokenRepository.expiredAllToken(existsUser.uid)
    }
  }

  void modifyPwd(String uid, String oldPassword, String newPassword) {
    if (!oldPassword || !newPassword) {
      throw new IllegalArgumentException("老密码和新密码都不能为空")
    }

    def existsUser = userRepository.findByUid(uid)
    if (existsUser.passwordHash != oldPassword) {
      throw new IllegalArgumentException("密码错误")
    }

    User newMember = new User(id: existsUser.id, passwordHash: newPassword)

    userRepository.modifyUser(newMember)
  }

  MemberInfo getMemberInfo(String uid) {
    def user = userRepository.findByUid(uid)
    return new MemberInfo(
        nickname: user.nickname,
        avatar: user.avatar,
        username: user.username,
        invitationCode: user.invitationCode
    )
  }

  PageList<InvitationMemberInfo> getInvitationMemberInfo(String uid, int currentPage, int pageSize) {
    Page<InvitationMemberInfo> page = PageHelper.startPage(currentPage, pageSize);
    def user = userRepository.findByUid(uid)

    List<User> from = userRepository.findUserByInvitationFrom(user.invitationCode)

    if (CollectionUtil.isEmpty(from)) {
      return PageList<WalletInfoRecord>.of(currentPage, pageSize, page.getTotal(), [] as List<InvitationMemberInfo>)
    } else {
      def collect = from.stream().map(it -> {
        return new InvitationMemberInfo(nickname: it.nickname, username: it.username, invitationDate: it.createTime)
      }).collect(Collectors.toList())

      return PageList<InvitationMemberInfo>.of(currentPage, pageSize, page.getTotal(), collect)
    }
  }

  CommonResponse<String> modifyAvatarAndNickname(String uid, String avatar, String nickname) {
    if (!avatar || !nickname) {
      return new CommonResponse<String>(status: CommonResponse.Fail, message: "头像或者昵称不能为空")
    }

    def user = userRepository.findByUid(uid)
    User newMember = new User(id: user.id, avatar: avatar, nickname: nickname)
    userRepository.modifyUser(newMember)
    return new CommonResponse<String>(status: CommonResponse.Success, message: "修改成功")

  }
}

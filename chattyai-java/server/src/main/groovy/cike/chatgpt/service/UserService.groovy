package cike.chatgpt.service

import cike.chatgpt.controller.PageList
import cike.chatgpt.repository.UserRepository
import cike.chatgpt.repository.entity.User
import cike.chatgpt.utils.NanoIdUtils
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.google.common.base.Preconditions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserService {

  @Autowired
  private UserRepository userRepository

  PageList<User> pageList(int currentPage, int pageSize, String username) {
    Page<Object> page = PageHelper.startPage(currentPage, pageSize);
    List<User> result = userRepository.findByCondition(username)
    PageList<User>.of(currentPage, pageSize, page.getTotal(), result)
  }

  void addUser(User user) {
    Preconditions.checkArgument(user.username != null && user.username.length() >= 8, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(user.nickname != null && user.nickname.length() >= 2, "昵称错误，至少 2 位")
    Preconditions.checkArgument(user.passwordHash != null && user.passwordHash.length() > 8, "密码错误，至少 8 位")
    Preconditions.checkArgument(user.avatar != null && user.avatar.length() > 8 && user.avatar.startsWith("http"), "头像错误，至少 8 位且以 http 开头")
    Preconditions.checkArgument(user.status != null && (user.status == 1 || user.status == 2), "状态错误")
    Preconditions.checkArgument(userRepository.findByUsername(user.username) == null, "用户名已存在，不能添加")

    user.setId(null)
    user.setUid(NanoIdUtils.randomNanoId())
    userRepository.addUser(user)
  }

  void modifyUserWithoutPassword(User user) {
    Preconditions.checkArgument(user.id != null && user.id >= 0, "呦呦呦")
    Preconditions.checkArgument(user.username != null && user.username.length() >= 8, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(user.nickname != null && user.nickname.length() >= 2, "昵称错误，至少 2 位")
    Preconditions.checkArgument(user.avatar != null && user.avatar.length() > 8 && user.avatar.startsWith("http"), "头像错误，至少 8 位且以 http 开头")
    Preconditions.checkArgument(user.status != null && (user.status == 1 || user.status == 2), "状态错误")

    def existsUser = userRepository.findByUsername(user.username)
    if (existsUser != null && existsUser.getId() != user.id) {
      throw new IllegalArgumentException("用户名已经存在，不能修改")
    }

    user.setCreateTime(null)
    user.setUpdateTime(new Date())
    user.setPasswordHash(null)

    userRepository.modifyUser(user)
  }
}

package cike.chatgpt.service

import cike.chatgpt.controller.PageList
import cike.chatgpt.repository.UserRepository
import cike.chatgpt.repository.entity.User
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
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
    userRepository.addUser(user)

  }
}

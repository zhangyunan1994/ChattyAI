package cike.chatgpt.controller.dashboard

import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.controller.PageList
import cike.chatgpt.interceptor.Permission
import cike.chatgpt.interceptor.RequiredLogin
import cike.chatgpt.repository.entity.User
import cike.chatgpt.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard/user")
@RequiredLogin(permission = Permission.DASH)
class UserController {

  @Autowired
  private UserService userService

  @GetMapping("pageList")
  CommonResponse<PageList<User>> pageList(int currentPage,
                                          @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) String username) {
    if (pageSize == null) {
      pageSize = 10
    }

    return new CommonResponse<PageList<User>>(status: CommonResponse.Success, data: userService.pageList(currentPage, pageSize, username))
  }

  @PostMapping("add")
  CommonResponse<String> add(@RequestBody User user) {
    try {
      userService.addUser(user)
    } catch (IllegalArgumentException e) {
      return new CommonResponse<String>(status: CommonResponse.Fail, message: e.getMessage())
    }
    return new CommonResponse<String>(status: CommonResponse.Success)
  }

  @PostMapping("modify")
  CommonResponse<String> modify(@RequestBody User user) {
    try {
      userService.modifyUserWithoutPassword(user)
    } catch (IllegalArgumentException e) {
      return new CommonResponse<String>(status: CommonResponse.Fail, message: e.getMessage())
    }
    return new CommonResponse<String>(status: CommonResponse.Success)
  }

}

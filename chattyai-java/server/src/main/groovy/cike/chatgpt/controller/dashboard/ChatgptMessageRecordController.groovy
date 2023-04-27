package cike.chatgpt.controller.dashboard

import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.controller.PageList
import cike.chatgpt.interceptor.Permission
import cike.chatgpt.interceptor.RequiredLogin
import cike.chatgpt.repository.entity.ChatgptMessageRecord
import cike.chatgpt.service.ChatgptMessageRecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequiredLogin(permission = Permission.DASH)
@RestController
@RequestMapping("/dashboard/chat")
class ChatgptMessageRecordController {

  @Autowired
  private ChatgptMessageRecordService chatgptMessageRecordService;

  @GetMapping("pageList")
  CommonResponse<PageList<ChatgptMessageRecord>> pageList(int currentPage, int pageSize,
                                                          String searchText,
                                                          String startTime,
                                                          String endTime) {
    new CommonResponse<PageList<ChatgptMessageRecord>>(status: CommonResponse.Success, data: chatgptMessageRecordService.pageList(currentPage, pageSize, searchText, startTime, endTime))
  }


}

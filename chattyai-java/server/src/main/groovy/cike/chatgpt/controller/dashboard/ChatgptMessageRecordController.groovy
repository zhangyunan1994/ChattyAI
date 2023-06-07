package cike.chatgpt.controller.dashboard

import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.controller.PageList
import cike.chatgpt.interceptor.Permission
import cike.chatgpt.interceptor.RequiredLogin
import cike.chatgpt.repository.mapper.specific.ChatgptMessageSpecificRecord
import cike.chatgpt.service.ChatgptMessageRecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequiredLogin(permission = Permission.DASH)
@RestController
@RequestMapping("/dashboard/chat/history")
class ChatgptMessageRecordController {

  @Autowired
  private ChatgptMessageRecordService chatgptMessageRecordService;

  @GetMapping("pageList")
  CommonResponse<PageList<ChatgptMessageSpecificRecord>> pageList(int currentPage, int pageSize,
                                                                  String username,
                                                                  String searchText,
                                                                  String startTime,
                                                                  String endTime) {
    new CommonResponse<PageList<ChatgptMessageSpecificRecord>>(status: CommonResponse.Success, data:
        chatgptMessageRecordService.pageList(currentPage, pageSize, username, searchText, startTime, endTime))
  }


}

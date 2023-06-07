package cike.chatgpt.service

import cike.chatgpt.controller.PageList
import cike.chatgpt.repository.ChatGPTMessageRecordRepository
import cike.chatgpt.repository.mapper.specific.ChatgptMessageSpecificRecord
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChatgptMessageRecordService {

  @Autowired
  private ChatGPTMessageRecordRepository repository

  PageList<ChatgptMessageSpecificRecord> pageList(int currentPage, int pageSize,
                                                  String username,
                                                  String searchText,
                                                  String startTime,
                                                  String endTime) {
    Page<ChatgptMessageSpecificRecord> page = PageHelper.startPage(currentPage, pageSize);
    repository.queryChatRecord(username, searchText, startTime, endTime)
    PageList<ChatgptMessageSpecificRecord>.of(currentPage, pageSize, page.getTotal(), page.getResult())
  }
}

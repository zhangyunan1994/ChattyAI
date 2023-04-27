package cike.chatgpt.service

import cike.chatgpt.controller.PageList
import cike.chatgpt.repository.ChatGPTMessageRecordRepository
import cike.chatgpt.repository.entity.ChatgptMessageRecord
import cike.chatgpt.repository.entity.SensitiveWordsHitRecord
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChatgptMessageRecordService {

  @Autowired
  private ChatGPTMessageRecordRepository repository

  PageList<ChatgptMessageRecord> pageList(int currentPage, int pageSize,
                                          String searchText,
                                          String startTime,
                                          String endTime) {
    Page<ChatgptMessageRecord> page = PageHelper.startPage(currentPage, pageSize);
    repository.queryChatRecord(searchText, startTime, endTime)
    PageList<ChatgptMessageRecord>.of(currentPage, pageSize, page.getTotal(), page.getResult())

  }
}

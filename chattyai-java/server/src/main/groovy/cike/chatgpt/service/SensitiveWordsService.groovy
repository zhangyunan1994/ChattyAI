package cike.chatgpt.service

import cike.chatgpt.controller.PageList
import cike.chatgpt.repository.SensitiveWordsRepository
import cike.chatgpt.repository.entity.SensitiveWords
import cike.chatgpt.repository.entity.SensitiveWordsHitRecord
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.google.common.base.Preconditions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SensitiveWordsService {

  @Autowired
  private SensitiveWordsRepository sensitiveWordsRepository

  void addSensitiveWords(SensitiveWords sensitiveWords) {
    Preconditions.checkArgument(sensitiveWords.category != null && sensitiveWords.category.length() >= 2, "分组名称错误，至少 2 位")
    Preconditions.checkArgument(sensitiveWords.word != null && sensitiveWords.word.length() >= 2, "敏感词错误，至少 2 位")

    Date now = new Date()
    sensitiveWords.setId(null)
    sensitiveWords.setCreatedAt(now)
    sensitiveWords.setUpdatedAt(now)

    sensitiveWordsRepository.add(sensitiveWords)
  }

  void modifySensitiveWords(SensitiveWords sensitiveWords) {
    Preconditions.checkArgument(sensitiveWords.id != null && sensitiveWords.id >= 0, "呦呦呦")
    Preconditions.checkArgument(sensitiveWords.category != null && sensitiveWords.category.length() >= 2, "分组名称错误，至少 2 位")
    Preconditions.checkArgument(sensitiveWords.word != null && sensitiveWords.word.length() >= 2, "敏感词错误，至少 2 位")

    Date now = new Date()
    sensitiveWords.setCreatedAt(null)
    sensitiveWords.setUpdatedAt(now)

    sensitiveWordsRepository.modify(sensitiveWords)
  }


  PageList<SensitiveWordsHitRecord> hitRecordPageList(int currentPage, int pageSize,
                                                      String searchText,
                                                      String startTime,
                                                      String endTime) {
    Page<SensitiveWordsHitRecord> page = PageHelper.startPage(currentPage, pageSize);
    sensitiveWordsRepository.findHitRecord(searchText, startTime, endTime)
    PageList<SensitiveWordsHitRecord>.of(currentPage, pageSize, page.getTotal(), page.getResult())
  }


  PageList<SensitiveWords> pageList(int currentPage, int pageSize, String searchText, String startTime, String endTime) {
    Page<SensitiveWords> page = PageHelper.startPage(currentPage, pageSize)
    sensitiveWordsRepository.query(searchText, startTime, endTime)
    PageList<SensitiveWords>.of(currentPage, pageSize, page.getTotal(), page.getResult())
  }

  void deleteSensitiveWords(SensitiveWords sensitiveWords) {
    Preconditions.checkArgument(sensitiveWords.id != null && sensitiveWords.id >= 0, "呦呦呦")
    sensitiveWordsRepository.deleteSensitiveWords(sensitiveWords.id)
  }
}

package cike.chatgpt.repository.sensitive


import cike.chatgpt.repository.entity.SensitiveWordsHitRecord
import cike.chatgpt.repository.mapper.SensitiveWordsHitRecordMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class SensitiveWordsHitRecordRepository {

  @Autowired
  private SensitiveWordsHitRecordMapper sensitiveWordsHitRecordMapper


  void addHitRecord(String uid, String conversationId, String systemMessage, String userMessage) {
    SensitiveWordsHitRecord sensitiveWordsHitRecord = new SensitiveWordsHitRecord()
    sensitiveWordsHitRecord.setUid(uid)
    sensitiveWordsHitRecord.setConversationId(conversationId)
    sensitiveWordsHitRecord.setSystemMessage(systemMessage)
    sensitiveWordsHitRecord.setUserMessage(userMessage)
    sensitiveWordsHitRecord.setUid(uid)
    sensitiveWordsHitRecordMapper.insertSelective(sensitiveWordsHitRecord)
  }


}

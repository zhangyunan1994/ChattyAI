package cike.chatgpt.repository

import cike.chatgpt.config.SQLInstance
import cike.chatgpt.repository.entity.ChatgptMessageRecord
import cike.chatgpt.repository.entity.ChatgptMessageRecordExample
import cike.chatgpt.repository.mapper.ChatgptMessageRecordMapper
import cike.chatgpt.repository.mapper.specific.ChatgptMessageRecordSpecificMapper
import cike.chatgpt.repository.mapper.specific.ChatgptMessageSpecificRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import java.text.SimpleDateFormat

@Repository
class ChatGPTMessageRecordRepository {

  @Autowired
  private ChatgptMessageRecordMapper mapper

  @Autowired
  private ChatgptMessageRecordSpecificMapper specificMapper

  void addRecord(String uid,
                 String conversationId,
                 String systemMessage,
                 String role,
                 String content,
                 String messageId,
                 long created,
                 String model,
                 int contextCount,
                 int promptTokens) {
    def messageRecord = new ChatgptMessageRecord()

    messageRecord.uid = uid
    messageRecord.conversationId = conversationId
    messageRecord.systemMessage = systemMessage
    messageRecord.role = role
    messageRecord.roleMessage = content
    messageRecord.messageId = messageId
    messageRecord.created = created
    messageRecord.model = model
    messageRecord.contextCount = contextCount
    messageRecord.promptTokens = promptTokens

    mapper.insertSelective(messageRecord)

  }

  static List<ChatGPTMessage> getLastRecords(String uid, String conversationId, String messageId, int count) {
    def sql = """
            select id, role, role_message, status from (
            select id, role, role_message, status from chatgpt_message_record where id <=
            (select id from chatgpt_message_record where uid = ? and conversation_id = ? and message_id= ?)
            and uid = ? and conversation_id = ? order by id desc limit ${count}) as temp
            order by temp.id
            """
    List<ChatGPTMessage> result = []
    SQLInstance.sql.rows(sql, uid, conversationId, messageId, uid, conversationId).each { row ->
      result.add(new ChatGPTMessage(id: row.id as long,
          role: row.role,
          roleMessage: row.role_message,
          status: row.status as byte
      ))
    }
    return result
  }

  List<ChatgptMessageSpecificRecord> queryChatRecord(String username, String searchText, String startTime, String endTime) {
    specificMapper.find(username, searchText, startTime, endTime)
  }
}

class ChatGPTMessage {
  long id
  String role
  String roleMessage
  byte status
}

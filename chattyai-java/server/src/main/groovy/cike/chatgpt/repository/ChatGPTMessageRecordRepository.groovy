package cike.chatgpt.repository

import cike.chatgpt.config.SQLInstance
import cike.chatgpt.repository.entity.ChatgptMessageRecord
import cike.chatgpt.repository.entity.ChatgptMessageRecordExample
import cike.chatgpt.repository.mapper.ChatgptMessageRecordMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import java.text.SimpleDateFormat

@Repository
class ChatGPTMessageRecordRepository {

  @Autowired
  private ChatgptMessageRecordMapper mapper

  static void addRecord(String uid, String roomId, String onceConversationId, String systemMessage, String role, String content, String messageId, long created, String model) {
    SQLInstance.sql.executeInsert("""insert into chatgpt_message_record(uid, room_id, once_conversation_id, system_message, role, content, message_id, created, model) 
                 VALUES (?,?,?,?,?,?,?,?,?)""",
        uid, roomId, onceConversationId, systemMessage, role, content, messageId, created, model,
    )
  }

  static List<ChatGPTMessage> getLastRecords(String uid, String roomId, String messageId, int count) {
    def sql = """
            select id, role, content from (
            select id, role, content from chatgpt_message_record where id <=
            (select id from chatgpt_message_record where uid = ? and room_id = ? and message_id= ?)
            and uid = ? and room_id = ? order by id desc limit ${count}) as temp
            order by temp.id
            """
    List<ChatGPTMessage> result = []
    SQLInstance.sql.rows(sql, uid, roomId, messageId, uid, roomId).each { row ->
      result.add(new ChatGPTMessage(id: row.id as long,
          role: row.role,
          content: row.content
      ))
    }
    return result
  }

  List<ChatgptMessageRecord> queryChatRecord(String searchText, String startTime, String endTime) {

    ChatgptMessageRecordExample example = new ChatgptMessageRecordExample()

    ChatgptMessageRecordExample.Criteria criteria = example.createCriteria()

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    if (searchText) {
      criteria.andContentLike('%' + searchText + '%')
    }

    if (startTime) {
      criteria.andCreateTimeGreaterThanOrEqualTo(format.parse(startTime))
    }

    if (endTime) {
      criteria.andCreateTimeLessThanOrEqualTo(format.parse(endTime))
    }
    mapper.selectByExample(example)
  }
}

class ChatGPTMessage {
  long id
  String role
  String content
}

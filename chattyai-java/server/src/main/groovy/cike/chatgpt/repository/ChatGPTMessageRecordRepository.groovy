package cike.chatgpt.repository

import cike.chatgpt.config.SQLInstance

class ChatGPTMessageRecordRepository {
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
}

class ChatGPTMessage {
    long id
    String role
    String content
}

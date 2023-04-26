package cike.chatgpt.repository.sensitive

import cike.chatgpt.config.SQLInstance

class SensitiveWordsHitRecordRepository {

    static void addHitRecord(String uid, String roomId, String onceConversationId, String systemMessage, String role, String content, String messageId, long created, String model) {
        SQLInstance.sql.executeInsert("""
                insert into sensitive_words_hit_record(uid, room_id, once_conversation_id, system_message, role, content, message_id, created, model) 
                VALUES (?,?,?,?,?,?,?,?,?)
                """,
                uid, roomId, onceConversationId, systemMessage, role, content, messageId, created, model,
        )
    }


}

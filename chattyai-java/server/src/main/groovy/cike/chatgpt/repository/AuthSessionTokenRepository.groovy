package cike.chatgpt.repository

import cike.chatgpt.config.SQLInstance
import org.apache.ibatis.jdbc.SQL
import org.springframework.stereotype.Repository

import java.time.LocalDateTime

@Repository
class AuthSessionTokenRepository {

    void expiredAllToken(String uid) {
        SQLInstance.sql.execute("update auth_session_token set expired_time = now() where user_id = ? and expired_time > now()", uid)
    }

    static AuthSessionToken findByUid(String uid) {
        def row = SQLInstance.sql.firstRow("select id, token, user_id, create_time, expired_time from auth_session_token where token = ?", uid)
        if (row) {
            return new AuthSessionToken(token: row.token, userId: row.user_id, createTime: row.create_time as LocalDateTime, expiredTime: row.expired_time as LocalDateTime)
        }
        return null
    }

    static void addRecord(String token, String userId, LocalDateTime expiredTime) {
        SQLInstance.sql.executeInsert("insert into auth_session_token(token, user_id, create_time, expired_time) VALUES (?,?,?,?)",
                token, userId, LocalDateTime.now(), expiredTime)
    }

}

class AuthSessionToken {
    String token
    String userId
    LocalDateTime createTime
    LocalDateTime expiredTime
}
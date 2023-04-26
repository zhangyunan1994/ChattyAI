package cike.chatgpt.repository

import cike.chatgpt.config.SQLInstance

import java.time.LocalDateTime

class AuthSessionTokenRepository {

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
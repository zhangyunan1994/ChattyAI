package cike.chatgpt.repository

import cike.chatgpt.config.SQLInstance

class UserRepository {

    static User findByUsername(String username) {
        def row = SQLInstance.sql.firstRow("select uid, username, password, avatar, description from user where username = ?", username)
        if (row) {
            return new User(uid: row.uid, username: row.username, password: row.password, avatar: row.avatar, description: row.description)
        }
        return null
    }

    static User findByUid(String uid) {
        def row = SQLInstance.sql.firstRow("select uid, username, password, avatar, description from user where uid = ?", uid)
        if (row) {
            return new User(uid: row.uid, username: row.username, password: row.password, avatar: row.avatar, description: row.description)
        }
        return null
    }
}

class User {
    String uid
    String username
    String password
    String avatar
    String description
}

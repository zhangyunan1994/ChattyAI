package cike.chatgpt.repository

import cike.chatgpt.config.SQLInstance
import cike.chatgpt.repository.entity.UserExample
import cike.chatgpt.repository.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

  @Autowired
  private UserMapper userMapper

  static User findByUsername(String username) {
    def row = SQLInstance.sql.firstRow("select id, uid, username, password_hash, avatar, description from user where username = ?", username)
    if (row) {
      return new User(id: row.id as int, uid: row.uid, username: row.username, password: row.password_hash, avatar: row.avatar, description: row.description)
    }
    return null
  }

  static User findByUid(String uid) {
    def row = SQLInstance.sql.firstRow("select id, uid, username, password_hash, avatar, description from user where uid = ?", uid)
    if (row) {
      return new User(id: row.id as int, uid: row.uid, username: row.username, password: row.password_hash, avatar: row.avatar, description: row.description)
    }
    return null
  }

  List<cike.chatgpt.repository.entity.User> findByCondition(String username) {
    UserExample example = new UserExample()
    UserExample.Criteria criteria = example.createCriteria()
    if (username) {
      criteria.andUsernameLike("%" + username + "%")
    }
    example.setOrderByClause("id desc")
    userMapper.selectByExample(example)
  }

  void addUser(cike.chatgpt.repository.entity.User user) {
    userMapper.insertSelective(user)
  }

  void modifyUser(cike.chatgpt.repository.entity.User user) {
    userMapper.updateByPrimaryKeySelective(user)
  }
}

class User {
  Integer id
  String uid
  String username
  String password
  String avatar
  String description
}

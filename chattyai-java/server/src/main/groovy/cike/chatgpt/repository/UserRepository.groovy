package cike.chatgpt.repository


import cike.chatgpt.repository.entity.User
import cike.chatgpt.repository.entity.UserExample
import cike.chatgpt.repository.mapper.UserMapper
import cike.chatgpt.utils.CollectionUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import java.text.SimpleDateFormat

@Repository
class UserRepository {

  @Autowired
  private UserMapper userMapper

  User findById(int id) {
    return userMapper.selectByPrimaryKey(id)
  }

  User findByUsername(String username) {
    UserExample example = new UserExample()
    example.createCriteria().andUsernameEqualTo(username)
    CollectionUtil.getFirstElseNull(userMapper.selectByExample(example))
  }

  User findByEmail(String email) {
    UserExample example = new UserExample()
    example.createCriteria().andEmailEqualTo(email)
    CollectionUtil.getFirstElseNull(userMapper.selectByExample(example))
  }

  User findByUid(String uid) {
    UserExample example = new UserExample()
    example.createCriteria().andUidEqualTo(uid)
    CollectionUtil.getFirstElseNull(userMapper.selectByExample(example))
  }

  List<User> findByCondition(String searchText,
                             String startTime,
                             String endTime) {
    UserExample example = new UserExample()
    UserExample.Criteria criteria = example.createCriteria()
    if (searchText) {
      criteria.andUsernameLike("%" + searchText + "%")
    }

    if (endTime || startTime) {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      if (startTime) {
        criteria.andCreateTimeGreaterThanOrEqualTo(format.parse(startTime))
      }

      if (endTime) {
        criteria.andCreateTimeLessThanOrEqualTo(format.parse(endTime))
      }
    }



    example.setOrderByClause("id desc")
    userMapper.selectByExample(example)
  }

  void addUser(User user) {
    userMapper.insertSelective(user)
  }

  void modifyUser(User user) {
    userMapper.updateByPrimaryKeySelective(user)
  }

  User findUserByInvitationCode(String invitationCode) {
    UserExample example = new UserExample()
    example.createCriteria().andInvitationCodeEqualTo(invitationCode)
    CollectionUtil.getFirstElseNull(userMapper.selectByExample(example))
  }

  List<User> findUserByInvitationFrom(String invitationCode) {
    UserExample example = new UserExample()
    example.createCriteria().andInvitationFromEqualTo(invitationCode)
    userMapper.selectByExample(example)
  }
}

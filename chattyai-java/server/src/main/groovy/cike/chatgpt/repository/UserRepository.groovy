package cike.chatgpt.repository


import cike.chatgpt.repository.entity.User
import cike.chatgpt.repository.entity.UserExample
import cike.chatgpt.repository.mapper.UserMapper
import cike.chatgpt.utils.CollectionUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

  @Autowired
  private UserMapper userMapper

  User findByUsername(String username) {
    UserExample example = new UserExample()
    example.createCriteria().andUsernameEqualTo(username)
    CollectionUtil.getFirstElseNull(userMapper.selectByExample(example))
  }

  User findByUid(String uid) {
    UserExample example = new UserExample()
    example.createCriteria().andUidEqualTo(uid)
    CollectionUtil.getFirstElseNull(userMapper.selectByExample(example))
  }

  List<User> findByCondition(String username) {
    UserExample example = new UserExample()
    UserExample.Criteria criteria = example.createCriteria()
    if (username) {
      criteria.andUsernameLike("%" + username + "%")
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
}

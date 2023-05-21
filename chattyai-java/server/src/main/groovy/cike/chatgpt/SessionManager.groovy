package cike.chatgpt


import cike.chatgpt.repository.AuthSessionTokenRepository
import cike.chatgpt.repository.UserRepository
import cike.chatgpt.repository.UserStatusEnum
import cike.chatgpt.repository.entity.User
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.CacheLoader
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

/**
 * @author zhangyunan
 */
@Component
class SessionManager {

  private Cache<String, User> cache

  @Autowired
  private UserRepository userRepository

  @PostConstruct
  void init() {
    cache = Caffeine.newBuilder()
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .maximumSize(2_0000)
        .build(new CacheLoader<String, User>() {
          @Override
          User load(String s) throws Exception {
            def token = AuthSessionTokenRepository.findByUid(s)
            if (token && LocalDateTime.now().isBefore(token.expiredTime)) {
              def user = userRepository.findByUid(token.userId)
              if (user && user.getStatus() == UserStatusEnum.NORMAL.code) {
                return user
              }
            }
            null
          }
        })
  }


  User get(String key) {
    return cache.get(key);
  }

  void put(String key, User data) {
    cache.put(key, data);
  }

}

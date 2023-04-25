package cike.chatgpt

import cike.chatgpt.repository.User
import cike.chatgpt.repository.UserRepository
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.CacheLoader
import com.github.benmanes.caffeine.cache.Caffeine

import java.util.concurrent.TimeUnit

/**
 * @author zhangyunan
 */
class SessionManager {

    private SessionManager() {
    }

    private static final Cache<String, User> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.DAYS)
            .maximumSize(2_000)
            .build(new CacheLoader<String, User>() {
                @Override
                User load(String s) throws Exception {
                    return UserRepository.findByUid(s);
                }
            })

    static User get(String key) {
        return cache.get(key);
    }

    static void put(String key, User data) {
        cache.put(key, data);
    }

}

package cike.chatgpt.repository.sensitive

import cike.chatgpt.config.SQLInstance

class SensitiveWordsRepository {

    static Set<String> findAll() {
        Set<String> result = new HashSet<String>();
        SQLInstance.sql.rows("select word from sensitive_words").each {
            result.add(it.word as String);
        }
        return result
    }
}

package cike.chatgpt.repository

import cike.chatgpt.config.SQLInstance

import java.time.LocalDateTime

class OpenaiKeyConfigRepository {

  static List<OpenaiKeyConfig> findEnableKeys() {
    List<OpenaiKeyConfig> result = []
    SQLInstance.sql.rows("""select id, account_id, openai_key, total_use_token, total_use_money, status, 
                                        create_time, expired_time
                                 from openai_key_config where status=1""").each { row ->
      result.add(new OpenaiKeyConfig(
          id: row.id as long,
          accountId: row.account_id,
          openaiKey: row.openai_key,
          totalUseToken: row.total_use_token as int,
          totalUseMoney: row.total_use_money as int,
          status: row.status as byte,
          createTime: row.create_time as LocalDateTime,
          expiredTime: row.expired_time as LocalDateTime,
      ))
    }
    return result
  }
}

class OpenaiKeyConfig {
  long id
  String accountId
  String openaiKey
  int totalUseToken
  int totalUseMoney
  byte status
  LocalDateTime createTime
  LocalDateTime expiredTime
}

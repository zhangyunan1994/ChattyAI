package cike.chatgpt.repository

import cike.chatgpt.repository.entity.Bot
import cike.chatgpt.repository.entity.BotExample
import cike.chatgpt.repository.mapper.BotMapper
import cike.chatgpt.utils.CollectionUtil
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
class BotRepository {

  @Autowired
  private BotMapper botMapper

  Bot getByBotId(String botId) {
    BotExample example = new BotExample()
    example.createCriteria().andBotIdEqualTo(botId)
    return CollectionUtil.getFirstElseNull(botMapper.selectByExample(example))
  }


}

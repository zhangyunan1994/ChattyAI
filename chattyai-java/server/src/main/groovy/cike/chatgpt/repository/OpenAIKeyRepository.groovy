package cike.chatgpt.repository

import cike.chatgpt.repository.entity.OpenaiKeyConfig
import cike.chatgpt.repository.entity.OpenaiKeyConfigExample
import cike.chatgpt.repository.mapper.OpenaiKeyConfigMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class OpenAIKeyRepository {

  @Autowired
  private OpenaiKeyConfigMapper mapper

  List<OpenaiKeyConfig> findByCondition() {
    OpenaiKeyConfigExample example = new OpenaiKeyConfigExample()
    OpenaiKeyConfigExample.Criteria criteria = example.createCriteria()
    example.setOrderByClause("id desc")
    return mapper.selectByExample(example)
  }

  void add(OpenaiKeyConfig param) {
    mapper.insertSelective(param)
  }

  void modify(OpenaiKeyConfig param) {
    mapper.updateByPrimaryKeySelective(param)
  }

  void delete(long id) {
    mapper.deleteByPrimaryKey(id)
  }

  List<OpenaiKeyConfig> findEnableKey() {
    OpenaiKeyConfigExample example = new OpenaiKeyConfigExample()
    example.createCriteria().andStatusEqualTo(1 as byte).andExpiredTimeGreaterThan(new Date())
    return mapper.selectByExample(example)
  }

  List<OpenaiKeyConfig> findAll() {
    return mapper.selectByExample(null)
  }

  void updateUsageInfo(long id, double totalUseToken) {
    def config = new OpenaiKeyConfig()
    config.id = id
    config.totalUseToken = totalUseToken.intValue()
    config.totalUseMoney = totalUseToken.intValue()
    mapper.updateByPrimaryKeySelective(config)
  }
}

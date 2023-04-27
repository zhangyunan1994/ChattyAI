package cike.chatgpt.component.chat

import cike.chatgpt.config.OpenAIConfig
import cike.chatgpt.repository.OpenAIKeyRepository
import cike.chatgpt.utils.RandomUtil
import com.theokanning.openai.service.OpenAiService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import java.time.Duration

@Component
class OpenAiServicePool {

  @Autowired
  private OpenAIConfig openAIConfig

  @Autowired
  private OpenAIKeyRepository repository

  List<OpenAiService> enabledKeys = new ArrayList<>()

  @PostConstruct
  void init() {
    refresh()
  }

  OpenAiService getOne() {
    if (enabledKeys.size() == 0) {
      return null
    } else if (enabledKeys.size() == 1) {
      return enabledKeys.get(0)
    } else {
      int randomIndex = RandomUtil.nextInt(0, enabledKeys.size())
      return enabledKeys.get(randomIndex)
    }
  }

  void refresh() {
    def keyConfigs = repository.findEnableKey()
    if (keyConfigs == null || keyConfigs.size() == 0) {
      enabledKeys.clear()
    } else {
      List<OpenAiService> refreshKeys = new ArrayList<>(keyConfigs.size())
      for (final def config in keyConfigs) {
        refreshKeys.add(new OpenAiService(config.openaiKey, Duration.ofSeconds(openAIConfig.timeoutSeconds ?: 10)))
      }
      enabledKeys = refreshKeys
    }
  }
}

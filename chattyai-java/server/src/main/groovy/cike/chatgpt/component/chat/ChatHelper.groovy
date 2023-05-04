package cike.chatgpt.component.chat

import cike.chatgpt.config.OpenAIConfig
import cike.chatgpt.controller.RequestProps
import cike.chatgpt.repository.ChatGPTMessageRecordRepository
import cike.chatgpt.repository.sensitive.SensitiveWordsHitRecordRepository
import cike.chatgpt.repository.sensitive.SensitiveWordsRepository
import cike.chatgpt.utils.NanoIdUtils
import com.alibaba.fastjson.JSON
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.image.CreateImageRequest
import com.theokanning.openai.image.ImageResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody

import javax.annotation.PostConstruct
import java.nio.charset.StandardCharsets

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
class ChatHelper {

  static Logger log = LoggerFactory.getLogger(ChatHelper.class)

  @Autowired
  private OpenAiServicePool openAiServicePool

  @Autowired
  private OpenAIConfig openAIConfig

  private ChatStrategy chatStrategy
  private ImageStrategy imageStrategy
  private SensitiveWordsStrategy sensitiveWordsStrategy

  @PostConstruct
  void init() {
    chatStrategy = new ChatStrategy(openAIConfig, openAiServicePool)
    imageStrategy = new ImageStrategy(openAIConfig, openAiServicePool)
    sensitiveWordsStrategy = new SensitiveWordsStrategy(openAIConfig, openAiServicePool)
  }

  boolean isSensitiveWords(String systemMessage, String prompt) {
    for (final def word in SensitiveWordsRepository.findAll()) {
      if (systemMessage.contains(word) || prompt.contains(word)) {
        return true
      }
    }
    return false
  }

  boolean isCreateImage(String systemMessage, String prompt) {
    return prompt && prompt.startsWith('#image') && prompt.length() >= 9 && prompt.charAt(6) == (" " as char)
  }

  StreamingResponseBody sendChat(String userId, RequestProps requestParam) {
    StreamingResponseBody responseBody = (OutputStream outputStream) -> {
      try {
        if (isSensitiveWords(requestParam.systemMessage, requestParam.prompt)) {
          sensitiveWordsStrategy.dodo(outputStream, userId, requestParam)
        } else if (isCreateImage(requestParam.systemMessage, requestParam.prompt)) {
          imageStrategy.dodo(outputStream, userId, requestParam)
        } else {
          chatStrategy.dodo(outputStream, userId, requestParam)
        }
      } catch (Exception e) {
        log.info("emitter.completeWithError()", e)
      }
    }
    return responseBody
  }
}

abstract class GPTStrategy {

  OpenAiServicePool pool
  OpenAIConfig openAIConfig

  GPTStrategy(OpenAIConfig openAIConfig, OpenAiServicePool pool) {
    this.pool = pool
    this.openAIConfig = openAIConfig
  }

  abstract dodo(OutputStream outputStream, String userId, RequestProps requestParam)
}

class SensitiveWordsStrategy extends GPTStrategy {
  static Logger log = LoggerFactory.getLogger(SensitiveWordsStrategy.class)

  SensitiveWordsStrategy(OpenAIConfig openAIConfig, OpenAiServicePool pool) {
    super(openAIConfig, pool)
  }

  @Override
  def dodo(OutputStream outputStream, String userId, RequestProps requestParam) {
    SensitiveWordsHitRecordRepository.addHitRecord(userId, requestParam.roomId, null, requestParam.systemMessage, "user", requestParam.prompt, null, System.currentTimeSeconds(), null)
    def id = NanoIdUtils.randomNanoId()
    def message = new ChatWebMessage(id: id, text: "很抱歉，我无法回答相关问题，", finishReason: "")
    outputStream.write(JSON.toJSONBytes(message))
    message = new ChatWebMessage(id: id, text: "很抱歉，我无法回答相关问题，请您尊重个人隐私，不要随意公开他人的个人信息。如果您有其他问题，我会尽力回答。", finishReason: "stop")
    outputStream.write(("\n" + JSON.toJSONString(message)).getBytes(StandardCharsets.UTF_8))
    outputStream.flush()
  }
}

class ImageStrategy extends GPTStrategy {
  static Logger log = LoggerFactory.getLogger(ImageStrategy.class)

  ImageStrategy(OpenAIConfig openAIConfig, OpenAiServicePool pool) {
    super(openAIConfig, pool)
  }

  @Override
  def dodo(OutputStream outputStream, String userId, RequestProps requestParam) {
    def messageDescription = requestParam.prompt.substring(7)

    def messageId = NanoIdUtils.randomNanoId()

    String firstText = "正常创建 ${messageDescription} 的图片, 请稍等..."

    def result = new ChatWebMessage(id: messageId,
        text: firstText,
        finishReason: "",
    )

    outputStream.write(JSON.toJSONBytes(result))
    outputStream.flush()

    def request = new CreateImageRequest(prompt: messageDescription, n: 1)
    ImageResult image = pool.getOne().createImage(request)

    messageId = NanoIdUtils.randomNanoId()

    def text = "${firstText}\n\n![${messageDescription}](${image.data.get(0).url})"

    result = new ChatWebMessage(id: messageId,
        text: text,
        role: "assistant",
        finishReason: "stop"
    )
    outputStream.write(("\n" + JSON.toJSONString(result)).getBytes(StandardCharsets.UTF_8))
    outputStream.flush()
  }
}

class ChatStrategy extends GPTStrategy {

  static Logger log = LoggerFactory.getLogger(ChatStrategy.class)

  ChatStrategy(OpenAIConfig openAIConfig, OpenAiServicePool pool) {
    super(openAIConfig, pool)
  }

  @Override
  def dodo(OutputStream outputStream, String userId, RequestProps requestParam) {
    final List<ChatMessage> messages = new ArrayList<>()
    final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
        requestParam.systemMessage ?: openAIConfig.defaultSystemPrompt)

    messages.add(systemMessage)

    if (requestParam.options.parentMessageId) {
      // 增加上下文
      def historyMessage = ChatGPTMessageRecordRepository.getLastRecords(userId, requestParam.roomId, requestParam.options.parentMessageId, 20)
      historyMessage.each {
        messages.add(new ChatMessage(it.role, it.content))
      }
    }

    final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), requestParam.prompt)
    messages.add(userMessage)

    ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
        .builder()
        .model(openAIConfig.model ?: "gpt-3.5-turbo")
        .messages(messages)
        .maxTokens(1000)
        .temperature(requestParam.temperature ?: 0.5D)
        .topP(requestParam.topP ?: 0.8D)
        .logitBias(new HashMap<>())
        .build()

    def onceConversationId = NanoIdUtils.randomNanoId()

    ChatGPTMessageRecordRepository.addRecord(userId, requestParam.roomId, onceConversationId, systemMessage.content, "user", userMessage.content, "sdf", System.currentTimeSeconds(), chatCompletionRequest.model)

    StringBuilder sb = new StringBuilder()
    boolean firstChunk = true
    String messageId = ""
    String model = ""
    long created = 0l
    pool.getOne().streamChatCompletion(chatCompletionRequest)
        .doOnError {
          log.info("emitter.completeWithError()", it)
          outputStream.flush()
        }
        .doOnComplete {
          log.info("emitter.complete()")
          outputStream.flush()
        }
        .blockingForEach {
          log.info("{}", it)
          messageId = it.getId()
          model = it.model
          created = it.created
          def content = it.choices.get(0).getMessage().getContent()
          if (content != null) {
            sb.append(content)
            def result = new ChatWebMessage(id: it.id,
                text: sb,
                finishReason: it.choices[0].finishReason
            )
            if (firstChunk) {
              outputStream.write(JSON.toJSONBytes(result))
              firstChunk = false
            } else {
              outputStream.write(("\n" + JSON.toJSONString(result)).getBytes(StandardCharsets.UTF_8))
            }
            outputStream.flush()
          }
        }
    ChatGPTMessageRecordRepository.addRecord(userId, requestParam.roomId, onceConversationId, systemMessage.content, "assistant", sb.toString(), messageId, created, model)

    log.info("{}", sb)
  }
}


class ChatWebMessage {
  String id
  String text
  String finishReason
}

package cike.chatgpt.component.chat

import cike.chatgpt.config.OpenAIConfig
import cike.chatgpt.controller.RequestProps
import cike.chatgpt.repository.ChatGPTMessageRecordRepository
import cike.chatgpt.repository.enums.ChatMessageRecordStatusEnum
import cike.chatgpt.repository.sensitive.SensitiveWordsHitRecordRepository
import cike.chatgpt.repository.sensitive.SensitiveWordsRepository
import cike.chatgpt.service.MemberWalletService
import cike.chatgpt.utils.NanoIdUtils
import cike.openai.TokenizerUtil
import com.alibaba.fastjson.JSON
import com.knuddels.jtokkit.api.ModelType
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

import java.nio.charset.StandardCharsets

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
class ChatHelper {

  static Logger log = LoggerFactory.getLogger(ChatHelper.class)

  @Autowired
  private OpenAiServicePool openAiServicePool

  @Autowired
  private OpenAIConfig openAIConfig

  @Autowired
  private ChatStrategy chatStrategy

  @Autowired
  private ImageStrategy imageStrategy

  @Autowired
  private SensitiveWordsStrategy sensitiveWordsStrategy

  boolean isSensitiveWords(String systemMessage, String prompt) {
    for (final def word in SensitiveWordsRepository.findAll()) {
      if (systemMessage.contains(word) || prompt.contains(word)) {
        return true
      }
    }
    return false
  }

  boolean isCreateImage(String systemMessage, String prompt) {
    return prompt
        && (prompt.startsWith('@image256x256 ') || prompt.startsWith('@image512x512 ') || prompt.startsWith('@image1024x1024 '))
        && prompt.trim().length() >= 18
  }

  StreamingResponseBody sendChat(String userId, RequestProps requestParam) {
    StreamingResponseBody responseBody = (OutputStream outputStream) -> {
      try {
        if (isSensitiveWords(openAIConfig.defaultSystemPrompt, requestParam.prompt)) {
          sensitiveWordsStrategy.dodo(outputStream, userId, requestParam)
        } else if (isCreateImage(openAIConfig.defaultSystemPrompt, requestParam.prompt)) {
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

@Component
class SensitiveWordsStrategy extends GPTStrategy {
  static Logger log = LoggerFactory.getLogger(SensitiveWordsStrategy.class)

  @Autowired
  private SensitiveWordsHitRecordRepository sensitiveWordsHitRecordRepository

  SensitiveWordsStrategy(@Autowired OpenAIConfig openAIConfig, @Autowired OpenAiServicePool pool) {
    super(openAIConfig, pool)
  }

  @Override
  def dodo(OutputStream outputStream, String userId, RequestProps requestParam) {
    sensitiveWordsHitRecordRepository.addHitRecord(userId, requestParam.conversationId, openAIConfig.defaultSystemPrompt, requestParam.prompt)
    def id = NanoIdUtils.randomNanoId()
    def message = new ChatWebMessage(id: id, text: "很抱歉，我无法回答相关问题，", finishReason: "")
    outputStream.write(JSON.toJSONBytes(message))
    message = new ChatWebMessage(id: id, text: "很抱歉，我无法回答相关问题，请您尊重个人隐私，不要随意公开他人的个人信息。如果您有其他问题，我会尽力回答。", finishReason: "stop")
    outputStream.write(("\n" + JSON.toJSONString(message)).getBytes(StandardCharsets.UTF_8))
    outputStream.flush()
  }
}

@Component
class ImageStrategy extends GPTStrategy {
  static Logger log = LoggerFactory.getLogger(ImageStrategy.class)

  ImageStrategy(@Autowired OpenAIConfig openAIConfig, @Autowired OpenAiServicePool pool) {
    super(openAIConfig, pool)
  }

  @Override
  def dodo(OutputStream outputStream, String userId, RequestProps requestParam) {
    String size = ""
    String imageDescription = ""
    def prompt = requestParam.prompt.trim()
    if (prompt.startsWith('@image256x256 ')) {
      size = "256x256"
      imageDescription = prompt.substring(14)
    } else if (prompt.startsWith('@image512x512 ')) {
      size = "512x512"
      imageDescription = prompt.substring(14)
    } else if (prompt.startsWith('@image1024x1024 ')) {
      size = "1024x1024"
      imageDescription = prompt.substring(16)
    }

    def messageId = NanoIdUtils.randomNanoId()

    String firstText = "正常创建 ${imageDescription} 的图片, 请稍等..."

    def result = new ChatWebMessage(id: messageId,
        text: firstText,
        finishReason: "",
    )

    outputStream.write(JSON.toJSONBytes(result))
    outputStream.flush()

    def request = new CreateImageRequest(prompt: imageDescription, n: 1, size: size)
    ImageResult image = pool.getOne().createImage(request)

    messageId = NanoIdUtils.randomNanoId()

    def text = "${firstText}\n\n![${imageDescription}](${image.data.get(0).url})"

    result = new ChatWebMessage(id: messageId,
        text: text,
        finishReason: "stop"
    )
    outputStream.write(("\n" + JSON.toJSONString(result)).getBytes(StandardCharsets.UTF_8))
    outputStream.flush()
  }
}

@Component
class ChatStrategy extends GPTStrategy {

  static Logger log = LoggerFactory.getLogger(ChatStrategy.class)

  @Autowired
  private ChatGPTMessageRecordRepository chatGPTMessageRecordRepository

  @Autowired
  private MemberWalletService memberWalletService

  ChatStrategy(@Autowired OpenAIConfig openAIConfig, @Autowired OpenAiServicePool pool) {
    super(openAIConfig, pool)
  }

  @Override
  def dodo(OutputStream outputStream, String userId, RequestProps requestParam) {
    final List<ChatMessage> messages = new ArrayList<>()
    final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), openAIConfig.defaultSystemPrompt)

    messages.add(systemMessage)

    int contextCount = 0

    if (requestParam.options.parentMessageId) {
      // 增加上下文
      def historyMessage = ChatGPTMessageRecordRepository.getLastRecords(userId, requestParam.conversationId, requestParam.options.parentMessageId, 20)
      historyMessage.each {
        if (it.role == ChatMessageRole.USER.value()) {
          contextCount++
          messages.add(new ChatMessage(ChatMessageRole.USER.value(), it.roleMessage))
        } else if (it.status == ChatMessageRecordStatusEnum.SUCCESS.code || it.status == ChatMessageRecordStatusEnum.SUCCESS_PART.code) {
          contextCount++
          messages.add(new ChatMessage(it.role, it.roleMessage))
        }
      }
    }

    final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), requestParam.prompt)
    messages.add(userMessage)

    ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
        .builder()
        .model(openAIConfig.model ?: "gpt-3.5-turbo")
        .messages(messages)
        .maxTokens(2000)
        .temperature(requestParam.temperature ?: 0.5D)
        .topP(requestParam.topP ?: 0.8D)
        .logitBias(new HashMap<>())
        .build()

    def userMessageId = NanoIdUtils.randomNanoId()

    def chatGPTPromptTokenCount = TokenizerUtil.numTokensFromMessages(messages, ModelType.GPT_3_5_TURBO)

    chatGPTMessageRecordRepository.addRecord(userId,
        requestParam.conversationId,
        openAIConfig.defaultSystemPrompt,
        ChatMessageRole.USER.value(),
        requestParam.prompt,
        userMessageId,
        System.currentTimeSeconds(),
        chatCompletionRequest.model,
        contextCount,
        chatGPTPromptTokenCount
    )

    memberWalletService.chattyAITokenSub(userId, chatGPTPromptTokenCount)

    StringBuilder sb = new StringBuilder()
    boolean firstChunk = true
    String messageId = ""
    String model = ""
    long created = 0l
    int index = 0
    ChatWebMessage lastMessage = null
    ChatWebMessage lastSendMessage = null
    pool.getOne().streamChatCompletion(chatCompletionRequest)
        .doOnError {
          if (it instanceof SocketException) {
            def result = new ChatWebMessage(id: NanoIdUtils.randomNanoId(),
                text: "Network Error",
                finishReason: "Network Error"
            )
            if (firstChunk) {
              outputStream.write(JSON.toJSONBytes(result))
              firstChunk = false
            } else {
              outputStream.write(("\n" + JSON.toJSONString(result)).getBytes(StandardCharsets.UTF_8))
            }
          }
          log.info("emitter.completeWithError()", it)
          outputStream.flush()
        }
        .doOnComplete {
          if (lastSendMessage != lastMessage) {
            outputStream.write(("\n" + JSON.toJSONString(lastMessage)).getBytes(StandardCharsets.UTF_8))
          }
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
            lastMessage = new ChatWebMessage(id: it.id,
                text: sb,
                finishReason: it.choices[0].finishReason
            )
            index++
            if (firstChunk) {
              lastSendMessage = lastMessage
              outputStream.write(JSON.toJSONBytes(lastMessage))
              firstChunk = false
            } else if (index % 5 == 0) {
              lastSendMessage = lastMessage
              outputStream.write(("\n" + JSON.toJSONString(lastMessage)).getBytes(StandardCharsets.UTF_8))
            }
            outputStream.flush()
          }
        }

    def gptSystemMessageTokenCount = TokenizerUtil.tokenCount(sb.toString())

    chatGPTMessageRecordRepository.addRecord(userId,
        requestParam.conversationId,
        openAIConfig.defaultSystemPrompt,
        ChatMessageRole.ASSISTANT.value(),
        sb.toString(),
        messageId,
        created, model, contextCount,
        gptSystemMessageTokenCount
    )

    memberWalletService.chattyAITokenSub(userId, gptSystemMessageTokenCount)
    log.info("{}", sb)
  }
}


class ChatWebMessage {
  String id
  String text
  String finishReason
}

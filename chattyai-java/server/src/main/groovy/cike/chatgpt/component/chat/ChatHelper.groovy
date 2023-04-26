package cike.chatgpt.component.chat


import cike.chatgpt.config.OpenAIConfig
import cike.chatgpt.controller.RequestProps
import cike.chatgpt.repository.ChatGPTMessageRecordRepository
import cike.chatgpt.repository.sensitive.SensitiveWordsHitRecordRepository
import cike.chatgpt.repository.sensitive.SensitiveWordsRepository
import cike.chatgpt.utils.NanoIdUtils
import com.alibaba.fastjson.JSON
import com.theokanning.openai.completion.chat.*
import com.theokanning.openai.image.CreateImageRequest
import com.theokanning.openai.image.ImageResult
import com.theokanning.openai.service.OpenAiService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody

import javax.annotation.PostConstruct
import java.nio.charset.StandardCharsets
import java.time.Duration

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
class ChatHelper {

    static Logger log = LoggerFactory.getLogger(ChatHelper.class)

    @Autowired
    private OpenAIConfig openAIConfig

    private ChatStrategy chatStrategy
    private ImageStrategy imageStrategy
    private SensitiveWordsStrategy sensitiveWordsStrategy

    @PostConstruct
    void init() {
        def service = new OpenAiService(openAIConfig.apiKey, Duration.ofSeconds(openAIConfig.timeoutSeconds ?: 10))

        chatStrategy = new ChatStrategy(openAIConfig, service)
        imageStrategy = new ImageStrategy(openAIConfig, service)
        sensitiveWordsStrategy = new SensitiveWordsStrategy(openAIConfig, service)
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

    OpenAIConfig openAIConfig
    OpenAiService service

    GPTStrategy(OpenAIConfig openAIConfig, OpenAiService service) {
        this.openAIConfig = openAIConfig
        this.service = service
    }

    abstract dodo(OutputStream outputStream, String userId, RequestProps requestParam)
}

class SensitiveWordsStrategy extends GPTStrategy {
    static Logger log = LoggerFactory.getLogger(SensitiveWordsStrategy.class)

    private static String SensitiveWordsPrompt = "{\"delta\":\"私。\",\"detail\":{\"choices\":[{\"index\":0,\"message\":{\"content\":\"私。\"}}],\"created\":1681904187,\"id\":\"chatcmpl-770OpRIV67apVWSljl5csWcNb3vKU\",\"model\":\"gpt-3.5-turbo-0301\",\"object\":\"chat.completion.chunk\"},\"id\":\"chatcmpl-770OpRIV67apVWSljl5csWcNb3vKU\",\"text\":\"## 该问题敏感不要问\\n\\n![](https://th.bing.com/th/id/OIP.HiR_mWL7XXgvsG5xA0RByAHaHa?pid=ImgDet&rs=1) \\n\\n注意数据安全和隐私安全 。我想提醒你，尊重他人的隐私是一种美德。在使用计算机和网络时，请遵守相关法律法规，不要非法侵入他人的隐私。\"}"

    SensitiveWordsStrategy(OpenAIConfig openAIConfig, OpenAiService service) {
        super(openAIConfig, service)
    }

    @Override
    def dodo(OutputStream outputStream, String userId, RequestProps requestParam) {
        SensitiveWordsHitRecordRepository.addHitRecord(userId, requestParam.roomId, null, requestParam.systemMessage, "user", requestParam.prompt, null, System.currentTimeSeconds(), null)
        outputStream.write(SensitiveWordsPrompt.getBytes(StandardCharsets.UTF_8))
        outputStream.flush()
    }
}

class ImageStrategy extends GPTStrategy {
    static Logger log = LoggerFactory.getLogger(ImageStrategy.class)

    ImageStrategy(OpenAIConfig openAIConfig, OpenAiService service) {
        super(openAIConfig, service)
    }

    @Override
    def dodo(OutputStream outputStream, String userId, RequestProps requestParam) {
        def messageDescription = requestParam.prompt.substring(7)

        def messageId = NanoIdUtils.randomNanoId()

        String firstText = "正常创建 ${messageDescription} 的图片, 请稍等..."

        def result = new ChatWebMessage(id: messageId,
                text: firstText,
                role: "assistant",
                delta: "...",
                detail: new ChatCompletionChunk(id: messageId,
                        object: "chat.completion.chunk",
                        created: System.currentTimeSeconds(),
                        model: "gpt-3.5-turbo-0301",
                        choices: [new ChatCompletionChoice(
                                index: 0,
                                message: new ChatMessage(role: "assistant", content: "..."),
                        )]
                )
        )

        outputStream.write(JSON.toJSONBytes(result))
        outputStream.flush()


        def request = new CreateImageRequest(prompt: messageDescription, n: 1)
        ImageResult image = service.createImage(request)

        messageId = NanoIdUtils.randomNanoId()

        def text = "${firstText}\n\n![${messageDescription}](${image.data.get(0).url})"

        result = new ChatWebMessage(id: messageId,
                text: text,
                role: "assistant",
                delta: ")",
                detail: new ChatCompletionChunk(id: messageId,
                        object: "chat.completion.chunk",
                        created: System.currentTimeSeconds(),
                        model: "gpt-3.5-turbo-0301",
                        choices: [new ChatCompletionChoice(
                                index: 0,
                                message: new ChatMessage(role: "assistant", content: ")"),
                                finishReason: "stop"
                        )]
                )
        )
        outputStream.write(("\n" + JSON.toJSONString(result)).getBytes(StandardCharsets.UTF_8))
        outputStream.flush()
    }
}

class ChatStrategy extends GPTStrategy {

    static Logger log = LoggerFactory.getLogger(ChatStrategy.class)

    ChatStrategy(OpenAIConfig openAIConfig, OpenAiService service) {
        super(openAIConfig, service)
    }

    @Override
    def dodo(OutputStream outputStream, String userId, RequestProps requestParam) {
        final List<ChatMessage> messages = new ArrayList<>()
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
                requestParam.systemMessage ?: openAIConfig.defaultSystemPrompt)

        messages.add(systemMessage)

        if (requestParam.getUseContext() && requestParam.options.parentMessageId) {
            // 增加上下文
            def historyMessage = ChatGPTMessageRecordRepository.getLastRecords(userId, requestParam.roomId, requestParam.options.parentMessageId, 10)
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
        service.streamChatCompletion(chatCompletionRequest)
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
                                role: it.choices[0].message.role,
                                delta: content,
                                detail: it
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
    String role
    String name
    String delta
    def detail
    String parentMessageId
    String conversationId
}

package cick

import cike.chatgpt.component.chat.ChatWebMessage
import com.alibaba.fastjson.JSON
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService
import org.junit.jupiter.api.Test

class ChatTest {

    @Test
    void test() {
        OpenAiService service = new OpenAiService("sk-");

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown.");
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "如何创建一个编程语言");
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .maxTokens(1000)
                .logitBias(new HashMap<>())
                .build();
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach( it -> {
                    println it
                });
    }
}

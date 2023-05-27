package cike.chatgpt.config

import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("openai")
@ToString
class OpenAIConfig {

    String defaultSystemPrompt = "You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown."
    String baseUrl = "https://api.openai.com"

    String model

    Long timeoutSeconds
}

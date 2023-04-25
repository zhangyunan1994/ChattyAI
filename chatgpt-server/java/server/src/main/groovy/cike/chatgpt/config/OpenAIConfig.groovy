package cike.chatgpt.config

import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@ConfigurationProperties("openai")
@ToString
class OpenAIConfig {

    String defaultSystemPrompt = "You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown."
    // OpenAI API Base URL - https://api.openai.com
    String baseUrl = "https://api.openai.com"

    // OpenAI API Key - https://platform.openai.com/overview
    String apiKey

    // change this to an `accessToken` extracted from the ChatGPT site's `https://chat.openai.com/api/auth/session` response
    String accessToken

    // OpenAI API Model - https://platform.openai.com/docs/models
    String model

    // set `true` to disable OpenAI API debug log
    boolean disableLog = false

    /**
     * Reverse Proxy - Available on accessToken
     # Default: https://bypass.churchless.tech/api/conversation
     # More: https://github.com/transitive-bullshit/chatgpt-api#reverse-proxy
     */
    String apiReverseProxy = "https://bypass.churchless.tech/api/conversation"

    Long timeoutSeconds
}

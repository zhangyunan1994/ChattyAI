package cike.openai;

import cike.openai.dashboard.billing.Usage;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.knuddels.jtokkit.api.ModelType;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class ChattyAIServiceTest {

  String baseUrl = "https://api.openai-proxy.com/";
  String userPrompt = "go 截取字符串指定长度";
  String systemPrompt = "You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown.";

  @BeforeAll
  void setup() {
//    baseUrl = "https://api.openai.com";
//    baseUrl = "http://52.15.253.100:38820";
  }

  @Test
  void dashboardBillingCreditGrants() {

    List<OpenAIAccount> openAIAccountList = new ArrayList<>();

    openAIAccountList.add(new OpenAIAccount("zz", "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"));

    DecimalFormat df = new DecimalFormat("#.000");

    for (OpenAIAccount openAIAccount : openAIAccountList) {
      ChattyAIService chattyAIService = new ChattyAIService(openAIAccount.key, Duration.ofSeconds(10),
          baseUrl);

      LocalDate endDate = LocalDate.now().plusDays(1);
      Usage usage = chattyAIService.dashboardBillingUsage(endDate.plusDays(-99), endDate);
      System.out.println(openAIAccount.account + "\t\t: " + df.format(usage.getTotalUsage() / 100) + "$");
    }
  }

  @Test
  void tokenDiff() {
    ChattyAIService chattyAIService = new ChattyAIService("sk-", Duration.ofSeconds(120),
        baseUrl);


    List<ChatMessage> messages = new ArrayList<>();
    ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemPrompt);

    messages.add(systemMessage);

    ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), userPrompt);
    messages.add(userMessage);

    ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
        .builder()
        .model("gpt-3.5-turbo")
        .messages(messages)
        .maxTokens(1000)
        .temperature(0.5D)
        .topP(0.8D)
        .logitBias(new HashMap<>())
        .build();

    ChatCompletionResult chatCompletion = chattyAIService.createChatCompletion(chatCompletionRequest);
    System.out.println(chatCompletion);
    System.out.println("-----------------chatCompletion---------------");
    System.out.println(chatCompletion.getUsage());
    System.out.println("-----------------TokenizerUtil---------------");
    System.out.println(TokenizerUtil.tokenCount(userPrompt));
    System.out.println(TokenizerUtil.tokenCount(systemPrompt));
    System.out.println(TokenizerUtil.tokenCount(chatCompletion.getChoices().get(0).getMessage().getContent()));
    System.out.println("-----------------TokenizerUtil---------------");
    System.out.println(TokenizerUtil.num_tokens_from_messages(messages, ModelType.GPT_3_5_TURBO));

  }

  @Test
  void tokens() {
    System.out.println(TokenizerUtil.tokenCount(userPrompt));
    System.out.println(TokenizerUtil.tokenCount(systemPrompt));
  }


}

class OpenAIAccount {

  String account;
  String key;

  public OpenAIAccount(String account, String key) {
    this.account = account;
    this.key = key;
  }
}

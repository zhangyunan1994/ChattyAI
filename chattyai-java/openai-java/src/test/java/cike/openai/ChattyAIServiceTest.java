package cike.openai;

import cike.openai.dashboard.billing.Usage;
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

  String key = "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";

  @BeforeAll
  void setup() {
//    baseUrl = "https://api.openai.com";
    baseUrl = "http://52.15.253.100:38820";

  }

  @Test
  void dashboardBillingCreditGrants() {

    List<OpenAIAccount> openAIAccountList = new ArrayList<>();

    openAIAccountList.add(new OpenAIAccount("zz", key));

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
    ChattyAIService chattyAIService = new ChattyAIService(key, Duration.ofSeconds(120),
        baseUrl);

    List<ChatMessage> messages = new ArrayList<>();
    ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemPrompt);
    messages.add(systemMessage);

    ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(),
        "我几个有一本书讲述一个二维空间的世界，好像居民都是几个图形。主人公是个正方形。有一天，一个来自名叫空间的三维球体跑来拜访这个正方形。平面国的居民眼看这个球体可以随意变化大小（进出平面），吓得目瞪口呆。这是哪本书？给我具体的介绍，并推荐几本类似的。");
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

    // ----------------------------------------------------------------
    ChatCompletionResult chatCompletion = chattyAIService.createChatCompletion(chatCompletionRequest);
    System.out.println(chatCompletion);
    System.out.println("-----------------chatCompletion---------------");
    System.out.println(chatCompletion.getUsage());
    System.out.println("-----------------TokenizerUtil---------------");
    System.out.println(TokenizerUtil.numTokensFromMessages(messages, ModelType.GPT_3_5_TURBO));
    System.out.println(TokenizerUtil.tokenCount(chatCompletion.getChoices().get(0).getMessage().getContent()));
    // ----------------------------------------------------------------

    messages.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(),
        chatCompletion.getChoices().get(0).getMessage().getContent()));
    messages.add(new ChatMessage(ChatMessageRole.USER.value(), "三体简介"));

    chatCompletionRequest = ChatCompletionRequest
        .builder()
        .model("gpt-3.5-turbo")
        .messages(messages)
        .maxTokens(1000)
        .temperature(0.5D)
        .topP(0.8D)
        .logitBias(new HashMap<>())
        .build();

    // ----------------------------------------------------------------
    chatCompletion = chattyAIService.createChatCompletion(chatCompletionRequest);
    System.out.println(chatCompletion);
    System.out.println("-----------------chatCompletion---------------");
    System.out.println(chatCompletion.getUsage());
    System.out.println("-----------------TokenizerUtil---------------");
    System.out.println(TokenizerUtil.numTokensFromMessages(messages, ModelType.GPT_3_5_TURBO));
    System.out.println(TokenizerUtil.tokenCount(chatCompletion.getChoices().get(0).getMessage().getContent()));
    System.out.println("-----------------TokenizerUtil---------------");
    // ----------------------------------------------------------------

    messages.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(),
        chatCompletion.getChoices().get(0).getMessage().getContent()));
    messages.add(new ChatMessage(ChatMessageRole.USER.value(), "三体中智子是神恶魔"));

    chatCompletionRequest = ChatCompletionRequest
        .builder()
        .model("gpt-3.5-turbo")
        .messages(messages)
        .maxTokens(1000)
        .temperature(0.5D)
        .topP(0.8D)
        .logitBias(new HashMap<>())
        .build();

    // ----------------------------------------------------------------
    chatCompletion = chattyAIService.createChatCompletion(chatCompletionRequest);
    System.out.println(chatCompletion);
    System.out.println("-----------------chatCompletion---------------");
    System.out.println(chatCompletion.getUsage());
    System.out.println("-----------------TokenizerUtil---------------");
    System.out.println(TokenizerUtil.numTokensFromMessages(messages, ModelType.GPT_3_5_TURBO));
    System.out.println(TokenizerUtil.tokenCount(chatCompletion.getChoices().get(0).getMessage().getContent()));
    System.out.println("-----------------TokenizerUtil---------------");
    // ----------------------------------------------------------------

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

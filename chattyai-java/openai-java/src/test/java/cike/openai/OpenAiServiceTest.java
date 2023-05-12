package cike.openai;

import cike.openai.dashboard.billing.Usage;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class OpenAiServiceTest {

  @org.junit.jupiter.api.Test
  void dashboardBillingCreditGrants() {

    String baseUrl = "https://api.openai-proxy.com/";
    baseUrl = "https://api.openai.com";
    baseUrl = "http://127.0.0.1:19090";

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
}

class OpenAIAccount {

  String account;
  String key;

  public OpenAIAccount(String account, String key) {
    this.account = account;
    this.key = key;
  }
}

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

    List<OpenAIAccount> openAIAccountList = new ArrayList<>();

    DecimalFormat df = new DecimalFormat("#.000");

    for (OpenAIAccount openAIAccount : openAIAccountList) {
      ChattyAIService chattyAIService = new ChattyAIService(openAIAccount.key, Duration.ofSeconds(10),
          "https://api.openai-proxy.com/");
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

package cike.openai;

import cike.openai.dashboard.billing.Usage;
import java.time.LocalDate;

class OpenAiServiceTest {

  @org.junit.jupiter.api.Test
  void dashboardBillingCreditGrants() {
    OpenAiService openAiService = new OpenAiService("sk-");

    LocalDate endDate = LocalDate.now().plusDays(1);
    Usage usage = openAiService.dashboardBillingUsage(endDate.plusDays(-99), endDate);
    System.out.println(usage);
  }
}

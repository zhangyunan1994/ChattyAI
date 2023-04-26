package cike.chatgpt.controller.dashboard


import cike.chatgpt.config.OpenAIConfig
import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.interceptor.Permission
import cike.chatgpt.interceptor.RequiredLogin
import cike.openai.OpenAiService
import cike.openai.dashboard.billing.Usage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.annotation.PostConstruct
import java.time.Duration
import java.time.LocalDate

@RestController
@RequestMapping("/dashboard/billing")
@RequiredLogin(permission = Permission.DASH)
class BillingController {

    @Autowired
    private OpenAIConfig openAIConfig;

    public OpenAiService openAiService;

    @PostConstruct
    void init() {
        openAiService = new OpenAiService(openAIConfig.apiKey, Duration.ofSeconds(openAIConfig.timeoutSeconds ?: 10))
    }

    @GetMapping("usage")
    CommonResponse<BillingUsage> usage() {
        LocalDate endDate = LocalDate.now().plusDays(1);
        try {
            Usage usage = openAiService.dashboardBillingUsage(endDate.plusDays(-99), endDate);
            return new CommonResponse<BillingUsage>(status: CommonResponse.Success, data: new BillingUsage(usage.totalUsage / 100))
        } catch (Exception e) {
            return new CommonResponse<BillingUsage>(status: CommonResponse.Fail, message: e.message)
        }

    }

}


class BillingUsage {
    Double totalUsage

    BillingUsage() {
    }

    BillingUsage(Double totalUsage) {
        this.totalUsage = totalUsage
    }
}

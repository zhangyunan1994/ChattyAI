package cike.chatgpt.controller.dashboard

import cike.chatgpt.config.OpenAIConfig
import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.interceptor.Permission
import cike.chatgpt.interceptor.RequiredLogin
import cike.chatgpt.repository.OpenAIKeyRepository
import cike.openai.ChattyAIService
import cike.openai.dashboard.billing.Usage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.time.Duration
import java.time.LocalDate

@RestController
@RequestMapping("/dashboard/billing")
@RequiredLogin(permission = Permission.DASH)
class BillingController {

    static Logger log = LoggerFactory.getLogger(BillingController.class)


    @Autowired
    private OpenAIConfig openAIConfig

    @Autowired
    private OpenAIKeyRepository repository

    @GetMapping("usage")
    CommonResponse<Map<String, BillingUsage>> usage() {
        LocalDate endDate = LocalDate.now().plusDays(1)
        LocalDate startDate = endDate.plusDays(-99)
        try {
            def keyConfigs = repository.findAll()
            Map<String, BillingUsage> result = new HashMap<String, BillingUsage>()
            // TODO 这里可以根据 key 的数量来确定是否并发
            for (final def config in keyConfigs) {
                try {
                    Usage usage = new ChattyAIService(config.openaiKey, Duration.ofSeconds(10), openAIConfig.baseUrl).dashboardBillingUsage(startDate, endDate)
                    result.put(config.accountId, new BillingUsage(usage.totalUsage / 100))

                    repository.updateUsageInfo(config.id, usage.totalUsage)

                } catch (Exception e) {
                    log.info("accountId {} fetch usage error", config.accountId, e)
                }
            }

            return new CommonResponse<Map<String, BillingUsage>>(status: CommonResponse.Success, data: result)
        } catch (Exception e) {
            return new CommonResponse<Map<String, BillingUsage>>(status: CommonResponse.Fail, message: e.message)
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

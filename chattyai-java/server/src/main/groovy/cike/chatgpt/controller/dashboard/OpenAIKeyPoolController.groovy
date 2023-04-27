package cike.chatgpt.controller.dashboard

import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.controller.PageList
import cike.chatgpt.interceptor.Permission
import cike.chatgpt.interceptor.RequiredLogin
import cike.chatgpt.repository.entity.OpenaiKeyConfig
import cike.chatgpt.service.OpenAIKeyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard/openai/key")
@RequiredLogin(permission = Permission.DASH)
class OpenAIKeyPoolController {

  @Autowired
  private OpenAIKeyService openAIKeyService

  @GetMapping("pageList")
  CommonResponse<PageList<OpenaiKeyConfig>> pageList(int currentPage, int pageSize) {
    new CommonResponse<PageList<OpenaiKeyConfig>>(status: CommonResponse.Success, data: openAIKeyService.pageList(currentPage, pageSize))
  }

  @PostMapping("add")
  CommonResponse<String> add(@RequestBody OpenaiKeyConfig param) {
    try {
      openAIKeyService.add(param)
    } catch (IllegalArgumentException e) {
      return new CommonResponse<String>(status: CommonResponse.Fail, message: e.getMessage())
    }
    return new CommonResponse<String>(status: CommonResponse.Success)
  }

  @PostMapping("modify")
  CommonResponse<String> modify(@RequestBody OpenaiKeyConfig param) {
    try {
      openAIKeyService.modify(param)
    } catch (IllegalArgumentException e) {
      return new CommonResponse<String>(status: CommonResponse.Fail, message: e.getMessage())
    }
    return new CommonResponse<String>(status: CommonResponse.Success)
  }

  @PostMapping("delete")
  CommonResponse<String> delete(@RequestBody OpenaiKeyConfig param) {
    try {
      openAIKeyService.delete(param.id)
    } catch (IllegalArgumentException e) {
      return new CommonResponse<String>(status: CommonResponse.Fail, message: e.getMessage())
    }
    return new CommonResponse<String>(status: CommonResponse.Success)
  }
}

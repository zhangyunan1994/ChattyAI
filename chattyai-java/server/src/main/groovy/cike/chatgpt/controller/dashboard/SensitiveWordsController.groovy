package cike.chatgpt.controller.dashboard

import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.controller.PageList
import cike.chatgpt.interceptor.Permission
import cike.chatgpt.interceptor.RequiredLogin
import cike.chatgpt.repository.entity.SensitiveWords
import cike.chatgpt.repository.entity.SensitiveWordsHitRecord
import cike.chatgpt.service.SensitiveWordsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard/sensitiveWords")
@RequiredLogin(permission = Permission.DASH)
class SensitiveWordsController {

  @Autowired
  private SensitiveWordsService sensitiveWordsService

  @GetMapping("pageList")
  CommonResponse<PageList<SensitiveWords>> pageList(int currentPage, int pageSize, @RequestParam(required = false) String searchText) {
    new CommonResponse<PageList<SensitiveWords>>(status: CommonResponse.Success, data: sensitiveWordsService.pageList(currentPage, pageSize, searchText))
  }

  @PostMapping("add")
  CommonResponse<String> add(@RequestBody SensitiveWords sensitiveWords) {
    try {
      sensitiveWordsService.addSensitiveWords(sensitiveWords)
    } catch (IllegalArgumentException e) {
      return new CommonResponse<String>(status: CommonResponse.Fail, message: e.getMessage())
    }
    return new CommonResponse<String>(status: CommonResponse.Success)
  }

  @PostMapping("modify")
  CommonResponse<String> modify(@RequestBody SensitiveWords sensitiveWords) {
    try {
      sensitiveWordsService.modifySensitiveWords(sensitiveWords)
    } catch (IllegalArgumentException e) {
      return new CommonResponse<String>(status: CommonResponse.Fail, message: e.getMessage())
    }
    return new CommonResponse<String>(status: CommonResponse.Success)
  }

  @PostMapping("delete")
  CommonResponse<String> delete(@RequestBody SensitiveWords sensitiveWords) {
    try {
      sensitiveWordsService.deleteSensitiveWords(sensitiveWords)
    } catch (IllegalArgumentException e) {
      return new CommonResponse<String>(status: CommonResponse.Fail, message: e.getMessage())
    }
    return new CommonResponse<String>(status: CommonResponse.Success)
  }


  @GetMapping("hitRecord")
  CommonResponse<PageList<SensitiveWordsHitRecord>> hitRecord(int currentPage, int pageSize,
                                                              String searchText,
                                                              String startTime,
                                                              String endTime) {

    new CommonResponse<PageList<SensitiveWordsHitRecord>>(status: CommonResponse.Success,
        data: sensitiveWordsService.hitRecordPageList(currentPage, pageSize, searchText, startTime, endTime))
  }

}

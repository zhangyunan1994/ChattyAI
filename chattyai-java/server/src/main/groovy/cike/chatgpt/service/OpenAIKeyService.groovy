package cike.chatgpt.service

import cike.chatgpt.controller.PageList
import cike.chatgpt.repository.OpenAIKeyRepository
import cike.chatgpt.repository.entity.OpenaiKeyConfig
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.google.common.base.Preconditions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OpenAIKeyService {

  @Autowired
  private OpenAIKeyRepository repository

  PageList<OpenaiKeyConfig> pageList(int currentPage, int pageSize) {
    Page<Object> page = PageHelper.startPage(currentPage, pageSize);
    List<OpenaiKeyConfig> result = repository.findByCondition()
    PageList<OpenaiKeyConfig>.of(currentPage, pageSize, page.getTotal(), result)
  }

  void add(OpenaiKeyConfig param) {
    Preconditions.checkArgument(param.accountId != null && param.accountId.length() >= 3, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(param.openaiKey != null && param.openaiKey.length() >= 3, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(param.status != null, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(param.expiredTime != null, "登录用户名错误，至少 8 位")

    param.setTotalUseMoney(null)
    param.setTotalUseToken(null)
    param.setId(null)
    repository.add(param)
  }

  void modify(OpenaiKeyConfig param) {
    Preconditions.checkArgument(param.id != null && param.id >= 0, "呦呦呦")
    Preconditions.checkArgument(param.accountId != null && param.accountId.length() >= 3, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(param.openaiKey != null && param.openaiKey.length() >= 3, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(param.status != null, "登录用户名错误，至少 8 位")
    Preconditions.checkArgument(param.expiredTime != null, "登录用户名错误，至少 8 位")

    param.setCreateTime(null)
    param.setTotalUseMoney(null)
    param.setTotalUseToken(null)

    repository.modify(param)
  }

  void delete(long id) {
    repository.delete(id)

  }
}

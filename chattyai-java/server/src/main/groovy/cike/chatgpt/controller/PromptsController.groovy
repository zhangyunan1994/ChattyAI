package cike.chatgpt.controller

import cike.chatgpt.interceptor.RequiredLogin
import cike.chatgpt.repository.Prompts
import cike.chatgpt.repository.PromptsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequiredLogin
@RestController
@RequestMapping("prompts")
class PromptsController {

  @GetMapping("lists")
  List<Prompts> lists() { return PromptsRepository.findAll() }

}

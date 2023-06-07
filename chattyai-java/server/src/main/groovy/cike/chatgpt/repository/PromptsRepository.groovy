package cike.chatgpt.repository

import cike.chatgpt.config.SQLInstance

class PromptsRepository {

  static List<Prompts> findAll() {

    List<Prompts> result = []
    SQLInstance.sql.rows("select `group`, sub_prompt, prompt from prompts").each { row ->
      result.add(new Prompts(group: row.group,
          subPrompt: row.sub_prompt,
          prompt: row.prompt
      ))
    }

    return result
  }

}

class Prompts {
  String group
  String subPrompt
  String prompt
}
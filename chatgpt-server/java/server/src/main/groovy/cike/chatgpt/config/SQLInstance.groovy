package cike.chatgpt.config


import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import javax.sql.DataSource

@Component
class SQLInstance {
  static Sql sql

  @Autowired
  DataSource dataSource

  @PostConstruct
  void init() {
    sql = new Sql(dataSource)
  }
}



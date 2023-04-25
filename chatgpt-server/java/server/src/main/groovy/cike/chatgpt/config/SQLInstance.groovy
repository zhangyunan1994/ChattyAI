package cike.chatgpt.config

import com.zaxxer.hikari.HikariDataSource
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class SQLInstance {
  static Sql sql

  @Autowired
  DatasourceConfig datasourceConfig

  @PostConstruct
  void init() {
    HikariDataSource ds = new HikariDataSource()
    ds.setJdbcUrl(datasourceConfig.url)
    ds.setUsername(datasourceConfig.username)
    ds.setPassword(datasourceConfig.password)
    ds.setMinimumIdle(datasourceConfig.minimumIdle)
    ds.setMaximumPoolSize(datasourceConfig.maximumPoolSize)
    sql = new Sql(ds)
  }
}



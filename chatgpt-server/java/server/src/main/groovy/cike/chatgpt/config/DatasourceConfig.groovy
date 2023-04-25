package cike.chatgpt.config

import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("datasource")
@ToString
class DatasourceConfig {
    String url
    String username
    String password
    int minimumIdle
    int maximumPoolSize
}

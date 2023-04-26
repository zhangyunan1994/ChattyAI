package cike.chatgpt

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@MapperScan("cike.chatgpt")
@SpringBootApplication
class Application {

  static void main(String[] args) {
    SpringApplication.run(Application.class, args)
  }

}

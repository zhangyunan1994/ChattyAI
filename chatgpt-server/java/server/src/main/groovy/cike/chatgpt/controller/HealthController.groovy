package cike.chatgpt.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("server")
class HealthController {

    @GetMapping("status")
    Pong pong() {
        return new Pong(status: "healthy", echo: "pong")
    }

}

class Pong {
    String status
    String echo
}

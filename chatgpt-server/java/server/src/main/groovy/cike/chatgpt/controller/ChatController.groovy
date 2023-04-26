package cike.chatgpt.controller


import cike.chatgpt.component.chat.ChatHelper
import cike.chatgpt.interceptor.Permission
import cike.chatgpt.interceptor.RequiredLogin
import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody

@RequiredLogin(permission = Permission.CHAT)
@Controller
@RequestMapping("chat")
class ChatController {

    static Logger log = LoggerFactory.getLogger(ChatController.class)

    @Autowired
    private ChatHelper chatHelper;

    @PostMapping(path = "stream")
    ResponseEntity<StreamingResponseBody> chatStream(@RequestBody RequestProps requestParam,
                                                     @RequestHeader("Authorization") String authorization) throws IOException {
        // 从文件系统中读取二进制文件内容
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String userId = authorization.substring(7)
        StreamingResponseBody streamingResponse = chatHelper.sendChat(userId, requestParam)
        return new ResponseEntity<>(streamingResponse, headers, HttpStatus.OK);
    }
}

class RequestProps {
    String prompt
    String roomId
    ChatContext options
    String systemMessage
    Double temperature
    @JsonProperty("top_p")
    Double topP
    boolean useContext = false
}

class ChatContext {
    String conversationId
    String parentMessageId
}


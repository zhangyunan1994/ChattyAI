package com.theokanning.openai


import com.theokanning.openai.image.CreateImageRequest
import com.theokanning.openai.image.ImageResult
import com.theokanning.openai.service.OpenAiService
import org.junit.jupiter.api.Test

import java.time.Duration

class ImageTest {

    @Test
    void createImage() throws Exception {
        OpenAiService service = new OpenAiService("sk-", Duration.ofSeconds(10))
        def request = new CreateImageRequest(prompt: "Generate a picture with Nacos letters", n: 1)
        ImageResult image = service.createImage(request)
        println image
    }

    @Test
    void testImagePrefix() {
        def str = "#image 创建一个胸有成竹的图片"

        println str.charAt(6)
        println str.substring(7)
        if (str.startsWith('#image') && str.length() >= 9 && str.charAt(6) == (" " as char)) {
            println("图片命令")
        }
    }
}

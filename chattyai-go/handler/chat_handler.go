package routers

import (
	"chattyai-go/helper"
	"encoding/json"
	"fmt"
	"github.com/gin-contrib/sse"
	"github.com/gin-gonic/gin"
	"io"
	"log"
	"strings"
)

type SteamRequest struct {
	Prompt         string                 `form:"prompt" binding:"required"`
	ConversationId string                 `form:"conversationId" binding:"required"`
	Options        map[string]interface{} `form:"options" binding:""`
	Temperature    float32                `form:"temperature" `
	TopP           float32                `form:"top_p" `
}

// ChatWebMessage 返回到端上的格式
type ChatWebMessage struct {
	Id           string `json:"id"`
	Text         string `json:"text"`
	FinishReason string `json:"finishReason"`
}

type ChatHandler struct {
	chatHelper *helper.ChatHelper
}

func NewChatHandler() *ChatHandler {
	return &ChatHandler{
		chatHelper: helper.NewChatHelper(),
	}
}

func (chatHandle *ChatHandler) ChatStream(c *gin.Context) {
	var request SteamRequest
	err := c.Bind(&request)
	if err != nil {
		log.Fatal(err.Error())
	}
	question := request.Prompt

	log.Println("request" + question)

	// TODO: 2023/5/29 查库追加上下文

	chanStream := make(chan string, 32)
	err = chatHandle.chatHelper.SendChat(helper.ChatRequest{
		Q:           question,
		Temperature: request.Temperature,
		TopP:        request.TopP,
	}, chanStream)

	if err != nil {
		c.Render(-1, sse.Event{Data: []byte(`something wrong`)})
	}

	var sb strings.Builder

	c.Stream(func(w io.Writer) bool {
		for {
			if msg, ok := <-chanStream; ok {
				if msg == "<!finish>" {
					break
				}
				if msg == "<!error>" {
					break
				}
				sb.WriteString(msg)
				message := ChatWebMessage{
					Id:   "",
					Text: sb.String(),
				}
				resp, _ := json.Marshal(message)
				c.Render(-1, sse.Event{Data: resp})
				fmt.Println(">>>" + string(resp))
				return true
			}
		}
		return false
	})

	// TODO:记录查询信息

}

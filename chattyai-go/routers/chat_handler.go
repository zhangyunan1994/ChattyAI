package routers

import (
	"context"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/gin-gonic/gin"
	gogpt "github.com/sashabaranov/go-openai"
	"io"
	"log"
)

type SteamRequest struct {
	Msg   string `form:"msg" binding:"required"`
	Chats string `form:"chats" binding:"required"`
}
type Chat struct {
	A string `form:"a"`
	Q string `form:"q"`
}

func ChatStream(c *gin.Context) {
	var request SteamRequest
	err := c.ShouldBindQuery(&request)
	if err != nil {
		log.Fatal(err.Error())
	}
	log.Println("request" + request.Msg)
	var chats []Chat
	errs := json.Unmarshal([]byte(request.Chats), &chats)
	if errs != nil {
		fmt.Println("json unmarshal error:", errs)
	}
	var question = ""
	if len(chats) != 0 {
		for i := 0; i < len(chats); i++ {
			chat := chats[i]
			question += chat.Q + "\n" + chat.A
		}
	}
	question += request.Msg

	client := gogpt.NewClient("you token")
	ctx := context.Background()

	req := gogpt.CompletionRequest{
		Model:     gogpt.GPT3TextDavinci003,
		MaxTokens: 5,
		Prompt:    question,
		Stream:    true,
	}
	stream, err := client.CreateCompletionStream(ctx, req)
	if err != nil {
		return
	}

	chanStream := make(chan string, 100)
	go func() {
		defer stream.Close()
		defer close(chanStream)
		for {
			response, err := stream.Recv()
			if errors.Is(err, io.EOF) {
				fmt.Println("Stream finished")
				chanStream <- "<!finish>"
				return
			}

			if err != nil {
				fmt.Printf("Stream error: %v\n", err)
				chanStream <- "<!error>"
				return
			}
			if len(response.Choices) == 0 {
				fmt.Println("Stream finished")
				chanStream <- "<!finish>"
				return
			}
			data, err := json.Marshal(response.Choices[0])
			chanStream <- string(data)
			fmt.Printf("Stream response: %v\n", response.Choices[0])
		}
	}()
	c.Stream(func(w io.Writer) bool {
		if msg, ok := <-chanStream; ok {
			if msg == "<!finish>" {
				c.SSEvent("stop", "finish")
			}
			if msg == "<!error>" {
				c.SSEvent("stop", "error")
			}
			c.SSEvent("message", msg)
			fmt.Printf("message: %v\n", msg)
			return true
		}
		return false
	})
}

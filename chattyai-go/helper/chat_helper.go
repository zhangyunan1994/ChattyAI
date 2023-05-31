package helper

import (
	"context"
	"errors"
	"fmt"
	openai "github.com/sashabaranov/go-openai"
	"io"
	"log"
)

type ChatHelper struct {
	swordsStrategy SensitiveWordsStrategy
	chatStrategy   ChatStrategy
}

// NewChatHelper 初始化ChatHelper
func NewChatHelper() *ChatHelper {
	return &ChatHelper{
		swordsStrategy: SensitiveWordsStrategy{},
		chatStrategy:   ChatStrategy{openaiClientPool: LoadOpenaiClientPool()},
	}
}

type ChatRequest struct {
	Q           string //问题，包含上下文
	Temperature float32
	TopP        float32
}

// GptStrategy 对话处理策略
type GptStrategy interface {
	Do(cr ChatRequest, chanStream chan string) error
}

func (c *ChatHelper) SendChat(cr ChatRequest, chanStream chan string) error {

	// TODO: 2023/5/29 敏感词检测
	return c.chatStrategy.Do(cr, chanStream)

}

// ChatStrategy 调用openapi处理对话
type ChatStrategy struct {
	openaiClientPool *OpenaiClientPool
}

func (c *ChatStrategy) Do(cr ChatRequest, chanStream chan string) error {

	//获取一个openai client
	client := c.openaiClientPool.GetOneClient()
	ctx := context.Background()

	req := openai.CompletionRequest{
		Model:       openai.GPT3TextDavinci003,
		MaxTokens:   500,
		Prompt:      cr.Q,
		Temperature: cr.Temperature,
		TopP:        cr.TopP,
		Stream:      true,
	}
	stream, err := client.CreateCompletionStream(ctx, req)
	if err != nil {
		log.Print(err)
		return errors.New("call openai error")
	}

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
			chanStream <- response.Choices[0].Text
		}
	}()

	return nil
}

// SensitiveWordsStrategy 包含敏感词的对话处理
type SensitiveWordsStrategy struct {
}

func (c *SensitiveWordsStrategy) Do(cr ChatRequest, chanStream chan string) error {
	//TODO implement me
	panic("implement me")
}

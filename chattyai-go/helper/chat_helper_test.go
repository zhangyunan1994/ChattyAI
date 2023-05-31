package helper

import (
	"encoding/json"
	"fmt"
	"strings"
	"testing"
)

func TestChatStrategy_Do(t *testing.T) {

	tests := []struct {
		q string
	}{
		// TODO: Add test cases.
		{q: "背诵静夜思"},
	}

	token := ""
	baseURL := "https://api.openai-proxy.com/v1"

	for _, tt := range tests {
		t.Run(tt.q, func(t *testing.T) {
			c := &ChatStrategy{
				openaiClientPool: LoadOneToOpenaiClientPool(baseURL, token),
			}
			chanStream := make(chan string, 100)
			c.Do(ChatRequest{Q: tt.q}, chanStream)

			var sb strings.Builder
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
					fmt.Println(string(resp))
				}
			}
		})
	}

	//output
	//{"id":"","text":"\n","finishReason":""}
	//{"id":"","text":"\n\n","finishReason":""}
	//{"id":"","text":"\n\n床","finishReason":""}
	//{"id":"","text":"\n\n床前","finishReason":""}
	//{"id":"","text":"\n\n床前明","finishReason":""}
	//{"id":"","text":"\n\n床前明月","finishReason":""}
	//{"id":"","text":"\n\n床前明月光","finishReason":""}
	//{"id":"","text":"\n\n床前明月光\n","finishReason":""}
	//{"id":"","text":"\n\n床前明月光\n疑","finishReason":""}
	//...
}

// ChatWebMessage 返回到端上的格式
type ChatWebMessage struct {
	Id           string `json:"id"`
	Text         string `json:"text"`
	FinishReason string `json:"finishReason"`
}

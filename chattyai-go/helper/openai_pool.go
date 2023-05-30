package helper

import (
	openai "github.com/sashabaranov/go-openai"
	"math/rand"
	"sync"
)

var once sync.Once
var pool *OpenaiClientPool
var isInit bool

type OpenaiClientPool struct {
	BaseUrl       string
	GptClientList []*openai.Client
}

// LoadOpenaiClientPool 获取openaiClient池
func LoadOpenaiClientPool() *OpenaiClientPool {
	once.Do(func() {
		pool = new(OpenaiClientPool)
		if !isInit {
			pool.initOpenaiClientPool()
			isInit = true
		}
	})
	return pool
}

// LoadOneToOpenaiClientPool  使用apikey,初始化一个openaiClient,通常用于测试
func LoadOneToOpenaiClientPool(baseUrl string, apiKey string) *OpenaiClientPool {
	once.Do(func() {
		pool = new(OpenaiClientPool)
		client := buildOpenaiClient(baseUrl, apiKey)
		pool.GptClientList = append(pool.GptClientList, client)
	})
	return pool
}

// InitOpenaiClientPool 初始化openaiClient池
func (p *OpenaiClientPool) initOpenaiClientPool() {
	// TODO: 2023/5/30 从db查询apikey构建openaiClient
	var apiKeys []string
	for _, key := range apiKeys {
		client := buildOpenaiClient("", key)
		p.GptClientList = append(p.GptClientList, client)
	}
}

// GetOneClient 获取一个openapi
func (p *OpenaiClientPool) GetOneClient() *openai.Client {

	poolSize := len(p.GptClientList)
	return p.GptClientList[rand.Intn(poolSize)]
}

func buildOpenaiClient(baseUrl string, apikey string) *openai.Client {
	config := openai.DefaultConfig(apikey)
	if len(baseUrl) > 0 {
		config.BaseURL = baseUrl
	}
	return openai.NewClientWithConfig(config)
}

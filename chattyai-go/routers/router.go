package routers

import (
	myhandler "chattyai-go/handler"
	"chattyai-go/middlewares"
	"chattyai-go/setting"
	"github.com/gin-gonic/gin"
)

type CommonResponse struct {
	Status  string      `json:"status"`
	Message string      `json:"message"`
	Data    interface{} `json:"data"`
}

func NewSuccessCommonResponse() *CommonResponse {
	return &CommonResponse{
		Status:  "Success",
		Message: "Success",
	}
}

func NewFailCommonResponse(message string) *CommonResponse {
	return &CommonResponse{
		Status:  "Fail",
		Message: message,
	}
}

func NewSuccessCommonResponseWithData(data interface{}) *CommonResponse {
	return &CommonResponse{
		Status:  "Success",
		Message: "Success",
		Data:    data,
	}
}

func InitRouter() *gin.Engine {

	chathandler := myhandler.NewChatHandler()

	r := gin.Default()
	r.BasePath()
	gin.SetMode(setting.ServerSetting.RunMode)
	r.Use(middlewares.Cors())

	r.GET("/", func(c *gin.Context) {
		c.Writer.WriteHeader(200)
		_, _ = c.Writer.Write([]byte("<html><body>star on <a href=\"https://github.com/zhangyunan1994/ChattyAI\"><img alt=\"GitHub Repo stars\" src=\"https://img.shields.io/github/stars/zhangyunan1994/chattyai?style=social\"></a></body></html>"))
		c.Writer.Header().Add("Accept", "text/html")
		c.Writer.Flush()
	})

	chattyaiGroup := r.Group("/chattyai")
	{
		{
			authGroup := chattyaiGroup.Group("auth")
			authGroup.POST("login/withPassword", LoginByPassword)
			authGroup.GET("checkToken", AuthCheckToken)
		}
		{
			chatGroup := chattyaiGroup.Group("chat")
			chatGroup.POST("stream", chathandler.ChatStream)
		}
	}

	return r
}

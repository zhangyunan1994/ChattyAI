package routers

import (
	myhandler "chattyai-go/handler"
	"chattyai-go/middlewares"
	"chattyai-go/setting"
	"github.com/gin-gonic/gin"
)

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
			authGroup.POST("login/withPassword", myhandler.LoginByPassword)
			authGroup.GET("checkToken", myhandler.AuthCheckToken)
		}
		{
			chatGroup := chattyaiGroup.Group("chat")
			chatGroup.POST("stream", chathandler.ChatStream)
		}
		{
			memberGroup := chattyaiGroup.Group("member")
			memberGroup.Use(middlewares.Auth("member"))
			memberGroup.GET("wallet", myhandler.GetTokenWallet)
			memberGroup.GET("info", myhandler.GetMemberInfo)
		}
		{
			memberGroup := chattyaiGroup.Group("bot")
			memberGroup.Use(middlewares.Auth("member"))
			memberGroup.GET("publicBot", myhandler.GetPublicBots)
		}
	}

	return r
}

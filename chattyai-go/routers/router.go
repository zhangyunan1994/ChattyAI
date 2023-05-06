package routers

import (
	"chattyai-go/middlewares"
	"github.com/gin-gonic/gin"
)

func InitRouter() *gin.Engine {
	r := gin.Default()
	r.BasePath()

	r.Use(middlewares.Cors())

	r.GET("/", func(c *gin.Context) {
		c.Writer.WriteHeader(200)
		_, _ = c.Writer.Write([]byte("<html><body>star on <a href=\"https://github.com/zhangyunan1994/ChattyAI\"><img alt=\"GitHub Repo stars\" src=\"https://img.shields.io/github/stars/zhangyunan1994/chattyai?style=social\"></a></body></html>"))
		c.Writer.Header().Add("Accept", "text/html")
		c.Writer.Flush()
	})

	chattyaiGroup := r.Group("/chattyai")
	{
		chatGroup := chattyaiGroup.Group("chat")
		{
			chatGroup.POST("chat")
		}
	}

	//quoteGroup := r.Group("/quote")
	//{
	//	quoteGroup.GET("/random", GetRandomQuote)
	//}
	//
	//sysDictGroup := r.Group("/sysdict")
	//{
	//	sysDictGroup.GET("/getByType", GetDictByTypeCode)
	//}
	//
	//guideLinkGroup := r.Group("/guide_link")
	//{
	//	guideLinkGroup.GET("list", GetAllLink)
	//}
	//
	//r.GET("/network_link/list", func(c *gin.Context) {
	//	links, err := models.GetAllNetworkCheckLinks()
	//	if err != nil {
	//		c.AbortWithError(500, err)
	//		return
	//	}
	//	c.JSON(200, links)
	//})
	return r
}

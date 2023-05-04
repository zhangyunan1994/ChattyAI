package main

import (
	"chattyai-go/models"
	"chattyai-go/setting"
	"github.com/gin-gonic/gin"
	"net/http"
	"os"
	"time"
)

func main() {
	args := os.Args

	profile := "test"

	if args != nil && len(args) > 1 {
		profile = args[1]
	}

	// 初始化数据库链接
	setting.Setup(profile)
	models.Setup()

	r := gin.Default()
	r.GET("/ping", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{
			"message": "pong",
		})
	})

	s := &http.Server{
		Addr:           ":38083",
		Handler:        r,
		ReadTimeout:    10 * time.Second,
		WriteTimeout:   10 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}
	err := s.ListenAndServe()
	if err != nil {
		return
	}
}

package main

import (
	"chattyai-go/job"
	"chattyai-go/models"
	"chattyai-go/routers"
	"chattyai-go/setting"
	"net/http"
	"os"
	"strconv"
	"time"
)

func main() {
	args := os.Args

	profile := "dev"

	if args != nil && len(args) > 1 {
		profile = args[1]
	}

	// 初始化数据库链接
	setting.Setup(profile)
	models.Setup()
	job.Init()

	routerInit := routers.InitRouter()

	s := &http.Server{
		Addr:           ":" + strconv.Itoa(setting.ServerSetting.HttpPort),
		Handler:        routerInit,
		ReadTimeout:    60 * time.Second,
		WriteTimeout:   60 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}
	err := s.ListenAndServe()
	if err != nil {
		return
	}
}

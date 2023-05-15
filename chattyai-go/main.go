package main

import (
	"chattyai-go/job"
	"chattyai-go/models"
	"chattyai-go/routers"
	"chattyai-go/setting"
	"fmt"
	"github.com/urfave/cli/v2"
	"log"
	"net/http"
	"os"
	"strconv"
	"time"
)

func main() {
	app := &cli.App{
		Name:  "greet",
		Usage: "fight the loneliness!",
		Flags: []cli.Flag{
			&cli.StringFlag{
				Name:    "config",
				Aliases: []string{"c"},
				Value:   "conf/chattyai.ini",
				Usage:   "Load configuration from `FILE`",
			},
		},
		Action: func(cCtx *cli.Context) error {
			fmt.Println(cCtx.String("config"))
			//
			// 初始化数据库链接
			setting.Setup(cCtx.String("config"))
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
			return err
		},
	}

	if err := app.Run(os.Args); err != nil {
		log.Fatal(err)
	}

}

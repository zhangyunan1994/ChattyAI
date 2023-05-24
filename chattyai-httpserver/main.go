package main

import (
	"fmt"
	"github.com/urfave/cli/v2"
	"log"
	"net/http"
	"os"
)

func main() {
	app := &cli.App{
		Name:  "greet",
		Usage: "fight the loneliness!",
		Flags: []cli.Flag{
			&cli.StringFlag{
				Name:    "filepath",
				Aliases: []string{"f"},
				Value:   "./",
				Usage:   "web 资源的目录",
			},
			&cli.StringFlag{
				Name:    "port",
				Aliases: []string{"p"},
				Value:   "8000",
				Usage:   "启动端口",
			},
		},
		Action: func(cCtx *cli.Context) error {
			fmt.Println(cCtx.String("filepath"))
			fmt.Println(cCtx.String("port"))
			http.Handle("/", http.FileServer(http.Dir(cCtx.String("filepath"))))
			err := http.ListenAndServe(":"+cCtx.String("port"), nil)
			return err
		},
	}

	if err := app.Run(os.Args); err != nil {
		log.Fatal(err)
	}
}

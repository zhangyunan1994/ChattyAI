package job

import (
	"fmt"
	"github.com/robfig/cron/v3"
	"time"
)

var c *cron.Cron

func Init() {
	c = cron.New()
	_, _ = c.AddFunc("*/1 * * * *", func() {
		fmt.Println("每分钟执行一次", time.Now())
	})
	c.Start()
}

func AddJob(spec string, cmd cron.Job) {
	_, _ = c.AddJob(spec, cmd)
}

package setting

import (
	"gopkg.in/ini.v1"
	"log"
)

type Server struct {
	RunMode  string
	HttpPort int
}

var ServerSetting = &Server{}

type Database struct {
	User     string
	Password string
	Host     string
	Name     string
}

var DatabaseSetting = &Database{}

var cfg *ini.File

func Setup(profile string) {
	cfg2, err := ini.Load("conf/app.ini")
	if err != nil {
		log.Fatalf("setting.Setup, fail to parse 'conf/app.ini': %v", err)
	}

	cfg = cfg2

	mapTo("server", ServerSetting)
	mapTo("database_"+profile, DatabaseSetting)
}

func mapTo(section string, v interface{}) {
	err := cfg.Section(section).MapTo(v)
	if err != nil {
		log.Fatalf("Cfg.MapTo RedisSetting err: %v", err)
	}
}

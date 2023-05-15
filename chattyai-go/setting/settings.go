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

type OpenAIConfig struct {
	BaseUrl        string
	Model          string
	TimeoutSeconds int
}

var OpenAIConfigSetting = &OpenAIConfig{}

var cfg *ini.File

func Setup(configFile string) {
	cfg2, err := ini.Load(configFile)
	if err != nil {
		log.Fatalf("setting.Setup, fail to parse '%s': %v", configFile, err)
	}

	cfg = cfg2

	mapTo("server", ServerSetting)
	mapTo("database", DatabaseSetting)
	mapTo("openai", OpenAIConfigSetting)
}

func mapTo(section string, v interface{}) {
	err := cfg.Section(section).MapTo(v)
	if err != nil {
		log.Fatalf("Cfg.MapTo RedisSetting err: %v", err)
	}
}

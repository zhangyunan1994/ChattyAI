package models

import (
	"database/sql"
	"errors"
	"gorm.io/gorm"
	"time"
)

type Bot struct {
	Id           uint           `gorm:"column:id;type:int(11) unsigned;AUTO_INCREMENT;primary_key" json:"id"`
	BotId        string         `gorm:"column:bot_id;type:varchar(30);unique;NOT NULL" json:"bot_id"`
	Uid          string         `gorm:"column:uid;type:varchar(30);NOT NULL" json:"uid"`
	AccessLevel  int            `gorm:"column:access_level;type:tinyint(4);default:1;comment:1 私有 2 公有;NOT NULL" json:"access_level"`
	Name         string         `gorm:"column:name;type:varchar(30);NOT NULL" json:"name"`
	Avatar       string         `gorm:"column:avatar;type:varchar(200);NOT NULL" json:"avatar"`
	SystemPrompt string         `gorm:"column:system_prompt;type:varchar(500);NOT NULL" json:"system_prompt"`
	Model        string         `gorm:"column:model;type:varchar(100);default:gpt-3.5-turbo;NOT NULL" json:"model"`
	MaxTokens    uint           `gorm:"column:max_tokens;type:int(11) unsigned;default:1500;NOT NULL" json:"max_tokens"`
	Temperature  uint           `gorm:"column:temperature;type:int(11) unsigned;default:10;NOT NULL" json:"temperature"`
	TopP         uint           `gorm:"column:top_p;type:int(11) unsigned;default:10;NOT NULL" json:"top_p"`
	Description  sql.NullString `gorm:"column:description;type:varchar(200)" json:"description"`
	CreateTime   time.Time      `gorm:"column:create_time;type:datetime;default:CURRENT_TIMESTAMP;NOT NULL" json:"create_time"`
	UpdateTime   time.Time      `gorm:"column:update_time;type:datetime;default:CURRENT_TIMESTAMP;NOT NULL" json:"update_time"`
}

func (m *Bot) TableName() string {
	return "bot"
}

func GetPublishBot(uid string) ([]Bot, error) {
	var u MemberWallet
	if err := db.Where(&Bot{AccessLevel: 2}).First(&u).Error; err == nil {
		return u, nil
	} else if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	} else {
		return nil, err
	}
}

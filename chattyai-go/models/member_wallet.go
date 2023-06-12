package models

import (
	"errors"
	"gorm.io/gorm"
	"time"
)

type MemberWallet struct {
	Id             uint      `gorm:"column:id;type:int(10) unsigned;primary_key;AUTO_INCREMENT" json:"id"`
	Uid            string    `gorm:"column:uid;type:varchar(30);comment:user 中 uid;NOT NULL" json:"uid"`
	Type           uint      `gorm:"column:type;type:tinyint(3) unsigned;comment:1 chat" json:"type"`
	TotalValue     int       `gorm:"column:total_value;type:int(11);default:0;NOT NULL" json:"totalValue"`
	AvailableValue int       `gorm:"column:available_value;type:int(11);default:0;NOT NULL" json:"availableValue"`
	CreateTime     time.Time `gorm:"column:create_time;type:datetime;default:CURRENT_TIMESTAMP" json:"createTime"`
	UpdateTime     time.Time `gorm:"column:update_time;type:datetime;default:CURRENT_TIMESTAMP" json:"updateTime"`
}

func (m *MemberWallet) TableName() string {
	return "member_wallet"
}

func GetTokenWalletByUid(uid string) (*MemberWallet, error) {
	var u MemberWallet
	if err := db.Where(&MemberWallet{Uid: uid, Type: 1}).First(&u).Error; err == nil {
		return &u, nil
	} else if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	} else {
		return nil, err
	}
}

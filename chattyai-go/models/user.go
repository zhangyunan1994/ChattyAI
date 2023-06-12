package models

import (
	"errors"
	"gorm.io/gorm"
	"time"
)

type User struct {
	Id             int       `gorm:"column:id;type:int(11);primary_key;AUTO_INCREMENT" json:"id"`
	Uid            string    `gorm:"column:uid;type:varchar(30);NOT NULL" json:"uid"`
	Username       string    `gorm:"column:username;type:varchar(20);NOT NULL" json:"username"`
	Role           string    `gorm:"column:role;type:varchar(10);default:user;comment:用户角色 user 普通用户；admin 管理员;NOT NULL" json:"role"`
	Nickname       string    `gorm:"column:nickname;type:varchar(20);comment:昵称" json:"nickname"`
	PasswordHash   string    `gorm:"column:password_hash;type:varchar(255);NOT NULL" json:"password_hash"`
	Email          string    `gorm:"column:email;type:varchar(50)" json:"email"`
	Avatar         string    `gorm:"column:avatar;type:varchar(300);comment:头像" json:"avatar"`
	Status         int       `gorm:"column:status;type:tinyint(4);default:1;comment:用户状态 1 正常 2 禁止登录;NOT NULL" json:"status"`
	Description    string    `gorm:"column:description;type:varchar(300);comment:用户简介" json:"description"`
	ExpiredTime    time.Time `gorm:"column:expired_time;type:datetime;comment:失效时间" json:"expired_time"`
	InvitationCode string    `gorm:"column:invitation_code;type:varchar(10)" json:"invitation_code"`
	InvitationFrom string    `gorm:"column:invitation_from;type:varchar(10)" json:"invitation_from"`
	CreateTime     time.Time `gorm:"column:create_time;type:datetime;default:CURRENT_TIMESTAMP" json:"create_time"`
	UpdateTime     time.Time `gorm:"column:update_time;type:datetime;default:CURRENT_TIMESTAMP" json:"update_time"`
}

func (User) TableName() string {
	return "user"
}

func GetUserByUsername(username string) (*User, error) {
	var u User
	if err := db.Where(&User{Username: username}).First(&u).Error; err == nil {
		return &u, nil
	} else if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	} else {
		return nil, err
	}
}

func GetUserByUid(uid string) (*User, error) {
	var u User
	if err := db.Where(&User{Uid: uid}).First(&u).Error; err == nil {
		return &u, nil
	} else if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	} else {
		return nil, err
	}
}

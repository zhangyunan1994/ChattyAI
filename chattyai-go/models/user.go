package models

import (
	"errors"
	"gorm.io/gorm"
	"time"
)

type User struct {
	ID           int       `gorm:"primaryKey;autoIncrement"`
	UID          string    `gorm:"unique;not null"`
	Username     string    `gorm:"unique;not null"`
	Nickname     string    `gorm:"default:null"`
	PasswordHash string    `gorm:"not null"`
	Email        string    `gorm:"default:null"`
	Avatar       string    `gorm:"default:null"`
	Status       int8      `gorm:"default:1;not null"`
	Description  string    `gorm:"default:null"`
	CreateTime   time.Time `gorm:"default:CURRENT_TIMESTAMP"`
	UpdateTime   time.Time `gorm:"default:CURRENT_TIMESTAMP;not null"`
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
	if err := db.Where(&User{UID: uid}).First(&u).Error; err == nil {
		return &u, nil
	} else if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	} else {
		return nil, err
	}
}

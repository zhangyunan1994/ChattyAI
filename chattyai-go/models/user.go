package models

import (
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
	Password     string    `gorm:"not null"`
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
	err := db.Where(&User{Username: username}).Find(&u).Error
	if err != nil && err != gorm.ErrRecordNotFound {
		return nil, err
	}
	return &u, nil
}

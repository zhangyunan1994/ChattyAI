package models

import (
	"errors"
	"gorm.io/gorm"
	"time"
)

type AuthSessionToken struct {
	ID          uint      `gorm:"primarykey"`
	Token       string    `gorm:"unique;not null"`
	UserID      string    `gorm:"not null"`
	CreateTime  time.Time `gorm:"default:CURRENT_TIMESTAMP"`
	ExpiredTime time.Time `gorm:"not null"`
}

func (AuthSessionToken) TableName() string {
	return "auth_session_token"
}

func CreateAuthToken(tokenId string, uid string) {
	db.Create(&AuthSessionToken{
		Token:       tokenId,
		UserID:      uid,
		CreateTime:  time.Now(),
		ExpiredTime: time.Now().Add(60 * 24 * 3 * time.Minute),
	})
}

func GetAuthTokenByToken(token string) (*AuthSessionToken, error) {
	var u AuthSessionToken
	if err := db.Where(&AuthSessionToken{Token: token}).First(&u).Error; err == nil {
		return &u, nil
	} else if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	} else {
		return nil, err
	}
}

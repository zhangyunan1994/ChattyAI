package models

import (
	"github.com/jinzhu/gorm"
)

type WelcomeQuote struct {
	Id      int
	Chinese string
	English string
}

func GetAllQuote() ([]*WelcomeQuote, error) {
	var quotes []*WelcomeQuote
	err := db.Find(&quotes).Error
	if err != nil && err != gorm.ErrRecordNotFound {
		return nil, err
	}
	return quotes, nil
}

package models

import "time"

type AuthSessionToken struct {
	Token       string
	UserID      string
	CreateTime  time.Time
	ExpiredTime time.Time
}

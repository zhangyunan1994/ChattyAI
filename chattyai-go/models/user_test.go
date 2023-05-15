package models

import (
	"chattyai-go/setting"
	"testing"
)

func TestGetUserByUsername(t *testing.T) {
	setting.Setup("../conf/chattyai.ini")
	Setup()
	user, _ := GetUserByUsername("zhangyunan")
	t.Log(user)
}

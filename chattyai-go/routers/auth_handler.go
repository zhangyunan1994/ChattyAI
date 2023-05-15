package routers

import (
	"github.com/gin-gonic/gin"
)

type LoginParam struct {
	Username   string `json:"username"`
	VerifyCode string `json:"verifyCode"`
	Password   string `json:"password"`
}

func LoginByPassword(c *gin.Context) {
	loginParam := LoginParam{}
	c.BindJSON(&loginParam)
	c.JSON(200, nil)
}

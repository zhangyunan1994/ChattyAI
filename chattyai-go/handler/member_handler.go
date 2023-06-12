package routers

import (
	"chattyai-go/models"
	"chattyai-go/models/common"
	"github.com/gin-gonic/gin"
)

type WalletInfo struct {
	TotalValue     int `json:"totalValue"`
	AvailableValue int `json:"availableValue"`
}

func GetTokenWallet(c *gin.Context) {
	currentUserId := c.GetString("current_user_id")
	wallet, err := models.GetTokenWalletByUid(currentUserId)
	if err != nil {
		c.JSON(200, common.NewFailCommonResponse(err.Error()))
	} else {
		c.JSON(200, common.NewSuccessCommonResponseWithData(WalletInfo{TotalValue: wallet.TotalValue, AvailableValue: wallet.AvailableValue}))
	}
}

func GetMemberInfo(c *gin.Context) {
	currentUserId := c.GetString("current_user_id")
	userinfo, err := models.GetUserByUid(currentUserId)
	if err != nil {
		c.JSON(200, common.NewFailCommonResponse(err.Error()))
	} else {
		c.JSON(200, common.NewSuccessCommonResponseWithData(MemberInfo{Nickname: userinfo.Nickname,
			Username:       userinfo.Username,
			Avatar:         userinfo.Avatar,
			InvitationCode: userinfo.InvitationCode,
		}))
	}
}

type MemberInfo struct {
	Nickname       string `json:"nickname"`
	Username       string `json:"username"`
	Avatar         string `json:"avatar"`
	InvitationCode string `json:"invitationCode"`
}

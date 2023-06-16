package routers

import (
	"chattyai-go/models"
	"chattyai-go/models/common"
	"github.com/gin-gonic/gin"
)

func GetPublicBots(c *gin.Context) {
	bots, err := models.GetPublishBot()
	if err != nil {
		c.JSON(200, common.NewFailCommonResponse(err.Error()))
	} else {
		result := make([]PublicBotInfo, len(bots))
		for index, bot := range bots {
			s := PublicBotInfo{
				BotId:       bot.BotId,
				Name:        bot.Name,
				Avatar:      bot.Avatar,
				Description: bot.Description.String,
			}
			result[index] = s
		}

		c.JSON(200, common.NewSuccessCommonResponseWithData(result))
	}
}

type PublicBotInfo struct {
	BotId       string `json:"botId"`
	Name        string `json:"name"`
	Avatar      string `json:"avatar"`
	Description string `json:"desc"`
}

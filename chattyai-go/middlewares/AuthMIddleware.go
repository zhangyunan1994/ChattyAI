package middlewares

import (
	"chattyai-go/models"
	"chattyai-go/models/common"
	"github.com/gin-gonic/gin"
	"net/http"
	"time"
)

func Auth(permission string) gin.HandlerFunc {
	return func(c *gin.Context) {
		token := c.Request.Header.Get("Authorization")
		if token == "" {
			c.AbortWithStatus(401)
			return
		}

		if len(token) != 57 {
			c.AbortWithStatus(401)
			return
		}

		if c.Request.Method == "OPTIONS" {
			c.AbortWithStatus(http.StatusNoContent)
			return
		}

		tokenId := token[7:]

		authToken, err := models.GetAuthTokenByToken(tokenId)
		if err != nil {
			c.JSON(200, common.NewFailCommonResponse(err.Error()))
			return
		} else if authToken == nil || authToken.ExpiredTime.Before(time.Now()) {
			c.JSON(200, common.NewFailCommonResponse("No Authorization"))
			return
		} else {
			c.Set("current_user_id", authToken.UserID)
		}

		c.Next()
	}
}

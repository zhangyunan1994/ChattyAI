package common

type CommonResponse struct {
	Status  string      `json:"status"`
	Message string      `json:"message"`
	Data    interface{} `json:"data"`
}

func NewSuccessCommonResponse() *CommonResponse {
	return &CommonResponse{
		Status:  "Success",
		Message: "Success",
	}
}

func NewFailCommonResponse(message string) *CommonResponse {
	return &CommonResponse{
		Status:  "Fail",
		Message: message,
	}
}

func NewSuccessCommonResponseWithData(data interface{}) *CommonResponse {
	return &CommonResponse{
		Status:  "Success",
		Message: "Success",
		Data:    data,
	}
}

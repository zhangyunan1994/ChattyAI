package utils

import (
	"crypto/rand"
	"encoding/hex"
)

const (
	alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-"
	size     = 21
)

func GenerateNanoId() (string, error) {
	bytes := make([]byte, size)
	_, err := rand.Read(bytes)
	if err != nil {
		return "", err
	}
	for i, b := range bytes {
		bytes[i] = alphabet[int(b)%len(alphabet)]
	}
	return hex.EncodeToString(bytes), nil
}

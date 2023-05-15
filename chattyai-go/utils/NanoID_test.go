package utils

import "testing"

func TestGenerateNanoId(t *testing.T) {
	id, _ := GenerateNanoId()
	t.Logf(id)
}

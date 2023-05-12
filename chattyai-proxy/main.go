package main

import (
	"fmt"
	"net/http"
	"net/http/httputil"
)

func main() {
	fmt.Println("Serve on :38820")

	director := func(req *http.Request) {
		req.URL.Scheme = "https"
		req.URL.Host = "api.openai.com"
	}

	http.Handle("/", &httputil.ReverseProxy{Director: director})

	err := http.ListenAndServe(":38820", nil)
	if err != nil {
		fmt.Println(err)
	}
}

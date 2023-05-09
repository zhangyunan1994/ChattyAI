package main

import (
	"fmt"
	"io"
	"net"
	"net/http"
	"strings"
)

type Proxy struct{}

func (p *Proxy) ServeHTTP(rw http.ResponseWriter, req *http.Request) {
	fmt.Printf("Received request %s %s %s\n", req.Method, req.Host, req.RemoteAddr)

	transport := http.DefaultTransport

	outReq := new(http.Request)
	*outReq = *req
	if clientIP, _, err := net.SplitHostPort(req.RemoteAddr); err == nil {
		if prior, ok := outReq.Header["X-Forwarded-For"]; ok {
			clientIP = strings.Join(prior, ", ") + ", " + clientIP
		}
		outReq.Header.Set("X-Forwarded-For", clientIP)
	}

	res, err := transport.RoundTrip(outReq)
	if err != nil {
		rw.WriteHeader(http.StatusBadGateway)
		return
	}

	for key, value := range res.Header {
		for _, v := range value {
			rw.Header().Add(key, v)
		}
	}
	rw.WriteHeader(res.StatusCode)
	_, err = io.Copy(rw, res.Body)
	if err != nil {
		return
	}
	err = res.Body.Close()
	if err != nil {
		return
	}
}

func main() {
	fmt.Println("Serve on :8080")
	http.Handle("/", &Proxy{})
	err := http.ListenAndServe("0.0.0.0:8080", nil)
	if err != nil {
		fmt.Println(err)
	}
}

package main

import (
	"fmt"
	"io"
	"log"
	"net/http"
	"net/url"
	"time"
)

var (
	target = "https://api.openai.com" // 目标域名
)

func main() {
	fmt.Println("Serve on :38820")
	http.HandleFunc("/", handleRequest)
	http.ListenAndServe(":38820", nil)
}

func handleRequest(w http.ResponseWriter, r *http.Request) {
	// 过滤无效URL
	_, err := url.Parse(r.URL.String())
	if err != nil {
		log.Println("Error parsing URL: ", err.Error())
		http.Error(w, http.StatusText(http.StatusInternalServerError), http.StatusInternalServerError)
		return
	}

	// 拼接目标URL
	targetURL := target + r.URL.Path

	// 创建代理HTTP请求
	proxyReq, err := http.NewRequest(r.Method, targetURL, r.Body)
	if err != nil {
		log.Println("Error creating proxy request: ", err.Error())
		http.Error(w, "Error creating proxy request", http.StatusInternalServerError)
		return
	}

	// 将原始请求头复制到新请求中
	for headerKey, headerValues := range r.Header {
		for _, headerValue := range headerValues {
			proxyReq.Header.Add(headerKey, headerValue)
		}
	}

	proxyReq.URL.RawQuery = r.URL.RawQuery

	// 默认超时时间设置为60s
	client := &http.Client{
		Timeout: 60 * time.Second,
	}

	// 向 OpenAI 发起代理请求
	resp, err := client.Do(proxyReq)
	if err != nil {
		log.Println("Error sending proxy request: ", err.Error())
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	defer resp.Body.Close()

	// 将响应头复制到代理响应头中
	for key, values := range resp.Header {
		for _, value := range values {
			w.Header().Add(key, value)
		}
	}

	// 将响应状态码设置为原始响应状态码
	w.WriteHeader(resp.StatusCode)

	// 将响应实体写入到响应流中（支持流式响应）
	buf := make([]byte, 1024)
	for {
		if n, err := resp.Body.Read(buf); err == io.EOF || n == 0 {
			return
		} else if err != nil {
			log.Println("error while reading respbody: ", err.Error())
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		} else {
			if _, err = w.Write(buf[:n]); err != nil {
				log.Println("error while writing resp: ", err.Error())
				http.Error(w, err.Error(), http.StatusInternalServerError)
				return
			}
			w.(http.Flusher).Flush()
		}
	}
}

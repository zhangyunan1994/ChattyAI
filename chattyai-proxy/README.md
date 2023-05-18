# chattyai-proxy

## 介绍


## 软件架构
软件架构说明


## 安装教程

1. 安装最新版的 Go SDK，要求 > 1.11 版本 (安装最新版就行了)
1. 设置环境变量 `GO111MODULE` 将其设置为 `on` 或 `auto`
1. 如果嫌网速慢，可设置代理 `GOPROXY` 为 `https://goproxy.io,direct`，提高下载速度

在 Go version > 1.13 时，可以通过以下方式配置

```sh

go env -w GOPROXY=https://goproxy.io,direct
go env -w GO111MODULE=on

```


## 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

## 参与贡献

1.  Clone 本仓库
2.  新建 feature_xxx 分支
3.  提交代码
4.  新建 mr 到 release 分支

## 打包

**linux**

```shell
GOOS=linux GOARCH=amd64 go build -o chattyai-proxy main.go
```

**windows**
```shell

```

**mac**
```shell

```
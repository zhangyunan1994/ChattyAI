# 项目部署

项目源码可以在 github 或者 gitee 获取

| 项目 | GitHub | Gitee |
|:--- |:--- |:--- |
| ChattyAI | [zhangyunan1994/ChattyAI](https://github.com/zhangyunan1994/ChattyAI) | [zhangyunan1994/ChattyAI](https://gitee.com/zhangyunan1994/ChattyAI) |
| chattyai-web-ui | [zhangyunan1994/chattyai-web-ui](https://github.com/zhangyunan1994/chattyai-web-ui) | [zhangyunan1994/chattyai-web-ui](https://gitee.com/zhangyunan1994/chattyai-web-ui) |
| chatgpt-web | [cike-projects/chatgpt-web](https://github.com/cike-projects/chatgpt-web.git) | [zhangyunan1994/chatgpt-web](https://gitee.com/zhangyunan1994/chatgpt-web) |

可以根据网络情况选择不同的仓库, 下面为了方便，统一使用 gitee 地址说明

假设我们的开发环境根路径为 `path/projects`

## 一、后端项目部署

首先获取源码

```shell
cd path/projects
git clone https://gitee.com/zhangyunan1994/ChattyAI.git
cd ChattyAI
```

> 后端项目分为 3 种语言编写，其中部署任意一种（java、go、py）即可.

项目依赖 MySQL 作为数据存储，需要先建数据库，SQL 脚本在 [docs/sql](docs/sql/) 中.

### 部署 Java 语言版本

在项目中可以看到 `chattyai-java`, 可以使用喜欢的 ide 进行打开，打开后可以发现如下目录
```
openai-java
pom.xml
server
```

其中 `openai-java` 为调用 `openai api` 的请求包，不需要部署。`server` 为后端项目，使用了 Spring Boot 技术，可以直接打包进行安装部署。开发时使用 JDK8，建议保持同一个版本。

在项目中有一个 application-template.yaml 的配置模版，可以根据实际情况进行配置. 配置完成后正常启动即可



## 二、用于用户的前端项目

前端项目打包需要安装 `node.js` 和 `pnpm`

**1. 安装 Node.js**

官网下载对应平台的安装包，直接安装即可 https://nodejs.org/zh-cn/download

安装完成后在终端输入 `node -v` 如果安装成功会输出安装的版本号

**2. 安装 pnpm**

在终端输入 `npm install -g pnpm`

安装完成后在终端输入 `pnpm -v` 如果安装成功会输出安装的版本号

**3. 获取代码**

首先获取项目

```shell
cd path/projects
git clone https://gitee.com/zhangyunan1994/chatgpt-web.git
cd chatgpt-web
## 安装依赖
pnpm install 
```

在项目中有一个 `.env` 文件，修改里面的  `VITE_APP_API_ALL_BASE_URL` 将值换成，刚才部署的 java 项目的访问地址，修改完成后运行 `pnpm build-only`, 命令执行成功后会出现一个 `dist` 文件夹，里面的文件就是需要部署的前端静态资源，可以使用 Nginx、Apache 等进行部署

## 三、后台管理功能的前端项目


前端项目打包需要安装 `node.js` 和 `pnpm`

**1. 安装 Node.js**

官网下载对应平台的安装包，直接安装即可 https://nodejs.org/zh-cn/download

安装完成后在终端输入 `node -v` 如果安装成功会输出安装的版本号

**2. 安装 pnpm**

在终端输入 `npm install -g pnpm`

安装完成后在终端输入 `pnpm -v` 如果安装成功会输出安装的版本号

**3. 获取代码**

首先获取项目

```shell
cd path/projects
git clone https://gitee.com/zhangyunan1994/chattyai-web-ui.git
cd chattyai-web-ui
## 安装依赖
pnpm install 
```

在项目中有一个 `.env.dev` 文件，修改里面的  `VITE_APP_BACKEND_URL` 将值换成，刚才部署的 java 项目的访问地址，修改完成后运行 `pnpm buildonlydev`, 命令执行成功后会出现一个 `dist` 文件夹，里面的文件就是需要部署的前端静态资源，可以使用 Nginx、Apache 等进行部署

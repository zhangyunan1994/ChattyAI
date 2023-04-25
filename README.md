# ChattyAI
This project is a ChatGPT UI system that utilizes OpenAI to provide a chatbot service with user management, billing, and sensitive word filtering. The system has a simple and user-friendly interface and is cloud-based for secure and stable operation globally.

![image](https://user-images.githubusercontent.com/27502286/233837555-cbff17ed-c2a9-4846-a263-20810e7e65f2.png)


```shell
git submodule init
git submodule update
```

## 如何部署

本项目分为 2 个前端和 1 个后端项目。

**其中 2 个前端项目：**

- @chatgpt-web 用于给用户使用的前端项目，主要提供聊天使用，可以到对应的项目查看部署文档。
- @chatgpt-manager-ui 主要用于给管理员使用，提供一部分配置和管理功能。如果不想部署这部分，可以通过修改数据库实现这部分的功能

**任选一个后端项目部署**

在 chatgpt-server 下有不同语言实现的后端 server，可以选择自己喜欢的语言部署

- java(groovy)
- go
- python


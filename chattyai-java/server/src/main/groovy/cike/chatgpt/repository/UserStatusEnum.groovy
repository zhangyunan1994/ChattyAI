package cike.chatgpt.repository

enum UserStatusEnum {
  NORMAL(1 as byte, "正常"),
  FORBIDDEN(2 as byte, "禁止登录"),
  EXPIRED(3 as byte, "账号过期")


  final byte code
  final String desc

  UserStatusEnum(byte code, String desc) {
    this.code = code
    this.desc = desc
  }
}
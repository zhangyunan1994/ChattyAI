package cike.chatgpt.repository

enum ChatMessageRecordStatusEnum {
  IN_GENERATION(1 as byte, "处理中"),
  SUCCESS(2 as byte, "成功"),
  SUCCESS_PART(3 as byte, "部分成功"),
  FAIL(4 as byte, "失败"),
  FAIL_TIMEOUT(5 as byte, "超时失败"),
  FAIL_OUT_TOKEN_LIMIT(6 as byte, "token 限制失败")

  final byte code
  final String desc

  ChatMessageRecordStatusEnum(byte code, String desc) {
    this.code = code
    this.desc = desc
  }
}
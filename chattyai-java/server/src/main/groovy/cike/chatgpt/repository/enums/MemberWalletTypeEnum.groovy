package cike.chatgpt.repository.enums

enum MemberWalletTypeEnum {
  CHAT(1 as byte, "CHAT")


  final byte code
  final String desc

    MemberWalletTypeEnum(byte code, String desc) {
    this.code = code
    this.desc = desc
  }
}
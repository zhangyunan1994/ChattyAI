package cike.chatgpt.repository.enums;

enum WalletInfoRecordTypeEnum {
  REGISTER(1 as byte, "注册奖励"),
  INVITATION_CODE(2 as byte, "邀请奖励"),
  CHAT(3 as byte, "系统消耗"),

  final byte code
  final String desc

  WalletInfoRecordTypeEnum(byte code, String desc) {
    this.code = code
    this.desc = desc
  }

  static WalletInfoRecordTypeEnum getByCode(byte code) {
    if (code) {
      for (final WalletInfoRecordTypeEnum role in values()) {
        if (role.code == code) return role
      }
    }
    return null
  }
}

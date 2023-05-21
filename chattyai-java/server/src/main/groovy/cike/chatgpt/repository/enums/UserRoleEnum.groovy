package cike.chatgpt.repository.enums

enum UserRoleEnum {
  USER("user"),
  ADMIN("admin")


  final String code

  UserRoleEnum(String code) {
    this.code = code
  }

  public static UserRoleEnum getByCode(String code) {
    if (code) {
      for (final def role in values()) {
        if (role.code == code) return role
      }
    }
    return null
  }
}
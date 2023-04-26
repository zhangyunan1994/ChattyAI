package cike.chatgpt.interceptor

enum Permission {

    IGNORE(""),
    CHAT("chat"),
    DASH("dash")

    private final String code

    Permission(String code) {
        this.code = code
    }

    String getCode() {
        return code
    }
}
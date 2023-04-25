package cike.chatgpt.controller

class CommonResponse<T> {

    public static String Success = "Success"
    public static String Fail = "Fail"

    String status;
    String message;
    T data
}

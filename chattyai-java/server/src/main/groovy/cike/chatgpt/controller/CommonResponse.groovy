package cike.chatgpt.controller

class CommonResponse<T> {

  public static String Success = "Success"
  public static String Fail = "Fail"

  String status
  String message
  T data

  static <T> CommonResponse<T> failure(String message) {
    new CommonResponse<T>(status: Fail, message: message)
  }

  static <T> CommonResponse<T> success(T data) {
    new CommonResponse<T>(status: Fail, data: data)
  }
}

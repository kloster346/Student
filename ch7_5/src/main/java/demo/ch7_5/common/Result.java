package demo.ch7_5.common;

import lombok.Data;

/**
 * 统一响应结果类
 */
@Data
public class Result<T> {
  private Integer code; // 状态码
  private String message; // 响应信息
  private T data; // 响应数据

  private Result(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  /**
   * 成功返回结果
   */
  public static <T> Result<T> success() {
    return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
  }

  /**
   * 成功返回结果
   * 
   * @param data 获取的数据
   */
  public static <T> Result<T> success(T data) {
    return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
  }

  /**
   * 成功返回结果
   * 
   * @param data    获取的数据
   * @param message 提示信息
   */
  public static <T> Result<T> success(T data, String message) {
    return new Result<>(ResultCode.SUCCESS.getCode(), message, data);
  }

  /**
   * 失败返回结果
   * 
   * @param errorCode 错误码
   */
  public static <T> Result<T> failed(ResultCode errorCode) {
    return new Result<>(errorCode.getCode(), errorCode.getMessage(), null);
  }

  /**
   * 失败返回结果
   * 
   * @param errorCode 错误码
   * @param message   错误信息
   */
  public static <T> Result<T> failed(ResultCode errorCode, String message) {
    return new Result<>(errorCode.getCode(), message, null);
  }

  /**
   * 失败返回结果
   * 
   * @param message 提示信息
   */
  public static <T> Result<T> failed(String message) {
    return new Result<>(ResultCode.FAILED.getCode(), message, null);
  }

  /**
   * 参数验证失败返回结果
   */
  public static <T> Result<T> validateFailed() {
    return failed(ResultCode.VALIDATE_FAILED);
  }

  /**
   * 参数验证失败返回结果
   * 
   * @param message 提示信息
   */
  public static <T> Result<T> validateFailed(String message) {
    return new Result<>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
  }
}
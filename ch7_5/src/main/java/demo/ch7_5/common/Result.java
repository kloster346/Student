package demo.ch7_5.common;

import lombok.Data;

/**
 * 通用响应结果
 */
@Data
public class Result<T> {
  /**
   * 状态码
   */
  private Integer code;

  /**
   * 响应消息
   */
  private String message;

  /**
   * 响应数据
   */
  private T data;

  private Result(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  /**
   * 成功响应
   */
  public static <T> Result<T> success(T data) {
    return new Result<>(200, "success", data);
  }

  /**
   * 失败响应
   */
  public static <T> Result<T> error(Integer code, String message) {
    return new Result<>(code, message, null);
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
   * 失败返回结果
   * 
   * @param businessCode 业务错误码
   */
  public static <T> Result<T> failed(BusinessCode businessCode) {
    return new Result<>(businessCode.getCode(), businessCode.getMessage(), null);
  }

  /**
   * 失败返回结果
   * 
   * @param businessCode 业务错误码
   * @param message      错误信息
   */
  public static <T> Result<T> failed(BusinessCode businessCode, String message) {
    return new Result<>(businessCode.getCode(), message, null);
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
package demo.ch7_5.common;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 处理验证异常
   */
  @ExceptionHandler(value = { MethodArgumentNotValidException.class, BindException.class })
  public Result<String> handleValidException(Exception e) {
    BindingResult bindingResult = null;
    if (e instanceof MethodArgumentNotValidException) {
      bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
    } else if (e instanceof BindException) {
      bindingResult = ((BindException) e).getBindingResult();
    }
    String message = null;
    if (bindingResult != null && bindingResult.hasErrors()) {
      FieldError fieldError = bindingResult.getFieldError();
      if (fieldError != null) {
        message = fieldError.getField() + fieldError.getDefaultMessage();
      }
    }
    return Result.validateFailed(message);
  }

  /**
   * 处理其他异常
   */
  @ExceptionHandler(value = Exception.class)
  public Result<String> handleException(Exception e) {
    return Result.failed(e.getMessage());
  }
}
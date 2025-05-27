package demo.ch7_5.common;

import lombok.Getter;

@Getter
public enum BusinessCode {
  STUDENT_EXISTS(1001, "学号已存在"),
  COURSE_EXISTS(1002, "课程号已存在"),
  SC_EXISTS(1003, "选课记录已存在"),
  SC_NOT_EXISTS(1004, "选课记录不存在");

  private final Integer code;
  private final String message;

  BusinessCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

}
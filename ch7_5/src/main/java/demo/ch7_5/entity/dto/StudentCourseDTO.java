package demo.ch7_5.entity.dto;

import lombok.Data;

/**
 * 学生选课信息DTO
 */
@Data
public class StudentCourseDTO {
  /**
   * 学号
   */
  private String studentNo;

  /**
   * 学生姓名
   */
  private String studentName;

  /**
   * 课程号
   */
  private String courseNo;

  /**
   * 课程名称
   */
  private String courseName;

  /**
   * 学分
   */
  private Integer credit;

  /**
   * 成绩
   */
  private Double grade;
}
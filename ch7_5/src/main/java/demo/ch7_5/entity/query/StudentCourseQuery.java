package demo.ch7_5.entity.query;

import lombok.Data;

/**
 * 学生选课查询参数
 */
@Data
public class StudentCourseQuery {
  /**
   * 学生姓名
   */
  private String studentName;

  /**
   * 课程名称
   */
  private String courseName;

  /**
   * 最低分数
   */
  private Double minGrade;

  /**
   * 最高分数
   */
  private Double maxGrade;

  /**
   * 页码
   */
  private Integer page = 1;

  /**
   * 每页大小
   */
  private Integer size = 10;

  /**
   * 排序字段
   */
  private String sortField;

  /**
   * 排序方式（asc/desc）
   */
  private String sortOrder;

  /**
   * 获取MySQL分页的起始位置
   */
  public Integer getPage() {
    return (page - 1) * size;
  }
}
package demo.ch7_5.service;

import demo.ch7_5.common.PageResult;
import demo.ch7_5.entity.dto.StudentCourseDTO;
import demo.ch7_5.entity.query.StudentCourseQuery;

/**
 * 学生选课Service接口
 */
public interface StudentCourseService {
  /**
   * 查询学生选课信息
   *
   * @param query 查询参数
   * @return 分页结果
   */
  PageResult<StudentCourseDTO> queryStudentCourses(StudentCourseQuery query);
}
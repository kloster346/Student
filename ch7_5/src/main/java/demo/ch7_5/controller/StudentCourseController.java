package demo.ch7_5.controller;

import demo.ch7_5.common.PageResult;
import demo.ch7_5.common.Result;
import demo.ch7_5.entity.dto.StudentCourseDTO;
import demo.ch7_5.entity.query.StudentCourseQuery;
import demo.ch7_5.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生选课Controller
 */
@RestController
@RequestMapping("/api/students/courses")
public class StudentCourseController {
  @Autowired
  private StudentCourseService studentCourseService;

  /**
   * 查询学生选课信息
   */
  @GetMapping
  public Result<PageResult<StudentCourseDTO>> queryStudentCourses(StudentCourseQuery query) {
    PageResult<StudentCourseDTO> pageResult = studentCourseService.queryStudentCourses(query);
    return Result.success(pageResult);
  }
}
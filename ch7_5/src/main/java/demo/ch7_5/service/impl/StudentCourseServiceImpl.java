package demo.ch7_5.service.impl;

import demo.ch7_5.common.PageResult;
import demo.ch7_5.entity.dto.StudentCourseDTO;
import demo.ch7_5.entity.query.StudentCourseQuery;
import demo.ch7_5.mapper.StudentCourseMapper;
import demo.ch7_5.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生选课Service实现类
 */
@Service
public class StudentCourseServiceImpl implements StudentCourseService {
  @Autowired
  private StudentCourseMapper studentCourseMapper;

  @Override
  public PageResult<StudentCourseDTO> queryStudentCourses(StudentCourseQuery query) {
    // 查询总记录数
    Long total = studentCourseMapper.countStudentCourses(query);
    if (total == 0) {
      return PageResult.empty();
    }

    // 查询数据列表
    List<StudentCourseDTO> list = studentCourseMapper.selectStudentCourses(query);
    return new PageResult<>(total, list);
  }
}
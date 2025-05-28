package demo.ch7_5.mapper;

import demo.ch7_5.entity.dto.StudentCourseDTO;
import demo.ch7_5.entity.query.StudentCourseQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生选课Mapper接口
 */
@Mapper
public interface StudentCourseMapper {
  /**
   * 查询学生选课信息
   *
   * @param query 查询参数
   * @return 学生选课信息列表
   */
  List<StudentCourseDTO> selectStudentCourses(@Param("query") StudentCourseQuery query);

  /**
   * 统计总记录数
   *
   * @param query 查询参数
   * @return 总记录数
   */
  Long countStudentCourses(@Param("query") StudentCourseQuery query);
}
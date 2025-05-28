package demo.ch7_5.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.ch7_5.common.BusinessCode;
import demo.ch7_5.common.Result;
import demo.ch7_5.common.PageResult;
import demo.ch7_5.entity.Course;
import demo.ch7_5.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
  @Autowired
  private CourseService courseService;

  /**
   * 获取课程列表（分页）
   */
  @GetMapping
  public Result<?> getCourses(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(required = false) String keyword) {
    Page<Course> pageParam = new Page<>(page, size);
    QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
    if (keyword != null && !keyword.isEmpty()) {
      queryWrapper.like("cname", keyword)
          .or()
          .like("cno", keyword)
          .or()
          .like("cdept", keyword);
    }
    Page<Course> result = courseService.page(pageParam, queryWrapper);
    return Result.success(new PageResult<>(result.getTotal(), result.getRecords()));
  }

  /**
   * 添加课程
   */
  @PostMapping
  public Result<?> addCourse(@RequestBody Course course) {
    // 检查课程号是否已存在
    if (courseService.getById(course.getCno()) != null) {
      return Result.failed(BusinessCode.COURSE_EXISTS);
    }
    if (courseService.save(course)) {
      return Result.success(null, "添加成功");
    }
    return Result.failed("添加失败");
  }

  /**
   * 更新课程信息
   */
  @PutMapping("/{cno}")
  public Result<?> updateCourse(@PathVariable String cno, @RequestBody Course course) {
    course.setCno(cno);
    if (courseService.updateById(course)) {
      return Result.success(null, "更新成功");
    }
    return Result.failed("更新失败");
  }

  /**
   * 删除课程
   */
  @DeleteMapping("/{cno}")
  public Result<?> deleteCourse(@PathVariable String cno) {
    if (courseService.removeById(cno)) {
      return Result.success(null, "删除成功");
    }
    return Result.failed("删除失败");
  }
}
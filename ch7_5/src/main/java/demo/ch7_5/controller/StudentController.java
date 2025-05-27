package demo.ch7_5.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.ch7_5.common.BusinessCode;
import demo.ch7_5.common.Result;
import demo.ch7_5.entity.Student;
import demo.ch7_5.service.StudentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {
  @Autowired
  private StudentService studentService;

  /**
   * 获取学生列表（分页）
   */
  @GetMapping
  public Result<?> getStudents(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(required = false) String keyword) {
    Page<Student> pageParam = new Page<>(page, size);
    QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
    if (keyword != null && !keyword.isEmpty()) {
      queryWrapper.like("sname", keyword)
          .or()
          .like("sno", keyword)
          .or()
          .like("sdept", keyword);
    }
    Page<Student> result = studentService.page(pageParam, queryWrapper);
    return Result.success(new PageResult<>(result.getTotal(), result.getRecords()));
  }

  /**
   * 添加学生
   */
  @PostMapping
  public Result<?> addStudent(@RequestBody Student student) {
    // 检查学号是否已存在
    if (studentService.getById(student.getSno()) != null) {
      return Result.failed(BusinessCode.STUDENT_EXISTS);
    }
    if (studentService.save(student)) {
      return Result.success(null, "添加成功");
    }
    return Result.failed("添加失败");
  }

  /**
   * 更新学生信息
   */
  @PutMapping("/{sno}")
  public Result<?> updateStudent(@PathVariable String sno, @RequestBody Student student) {
    student.setSno(sno);
    if (studentService.updateById(student)) {
      return Result.success(null, "更新成功");
    }
    return Result.failed("更新失败");
  }

  /**
   * 删除学生
   */
  @DeleteMapping("/{sno}")
  public Result<?> deleteStudent(@PathVariable String sno) {
    if (studentService.removeById(sno)) {
      return Result.success(null, "删除成功");
    }
    return Result.failed("删除失败");
  }
}

@Getter
class PageResult<T> {
  private long total;
  private java.util.List<T> list;

  public PageResult(long total, java.util.List<T> list) {
    this.total = total;
    this.list = list;
  }

}
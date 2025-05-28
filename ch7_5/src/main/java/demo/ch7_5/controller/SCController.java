package demo.ch7_5.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.ch7_5.common.BusinessCode;
import demo.ch7_5.common.Result;
import demo.ch7_5.common.PageResult;
import demo.ch7_5.entity.SC;
import demo.ch7_5.entity.dto.BatchDeleteSCDTO;
import demo.ch7_5.service.SCService;
import demo.ch7_5.service.StudentService;
import demo.ch7_5.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sc")
public class SCController {
  @Autowired
  private SCService scService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private CourseService courseService;

  /**
   * 获取选课列表（分页）
   */
  @GetMapping
  public Result<?> getSCList(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(required = false) String keyword) {
    Page<SC> pageParam = new Page<>(page, size);
    QueryWrapper<SC> queryWrapper = new QueryWrapper<>();
    if (keyword != null && !keyword.isEmpty()) {
      queryWrapper.like("sno", keyword)
          .or()
          .like("cno", keyword);
    }
    Page<SC> result = scService.page(pageParam, queryWrapper);
    return Result.success(new PageResult<>(result.getTotal(), result.getRecords()));
  }

  /**
   * 添加选课记录
   */
  @PostMapping
  public Result<?> addSC(@RequestBody SC sc) {
    // 检查学号是否存在
    if (studentService.getById(sc.getSno()) == null) {
      return Result.failed("学号不存在");
    }
    // 检查课程号是否存在
    if (courseService.getById(sc.getCno()) == null) {
      return Result.failed("课程号不存在");
    }
    // 检查是否已经选过这门课
    QueryWrapper<SC> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("sno", sc.getSno())
        .eq("cno", sc.getCno());
    if (scService.getOne(queryWrapper) != null) {
      return Result.failed(BusinessCode.SC_EXISTS);
    }
    if (scService.save(sc)) {
      return Result.success(null, "选课成功");
    }
    return Result.failed("选课失败");
  }

  /**
   * 更新成绩
   */
  @PutMapping("/{sno}/{cno}")
  public Result<?> updateGrade(
      @PathVariable String sno,
      @PathVariable String cno,
      @RequestBody SC sc) {
    QueryWrapper<SC> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("sno", sno)
        .eq("cno", cno);
    SC existingSC = scService.getOne(queryWrapper);
    if (existingSC == null) {
      return Result.failed(BusinessCode.SC_NOT_EXISTS);
    }
    existingSC.setGrade(sc.getGrade());
    if (scService.update(existingSC, queryWrapper)) {
      return Result.success(null, "成绩更新成功");
    }
    return Result.failed("成绩更新失败");
  }

  /**
   * 退课
   */
  @DeleteMapping("/{sno}/{cno}")
  public Result<?> deleteSC(
      @PathVariable String sno,
      @PathVariable String cno) {
    QueryWrapper<SC> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("sno", sno)
        .eq("cno", cno);
    if (scService.remove(queryWrapper)) {
      return Result.success(null, "退课成功");
    }
    return Result.failed("退课失败");
  }

  /**
   * 批量删除选课记录
   *
   * @param batchDeleteDTO 批量删除请求
   * @return 删除结果
   */
  @DeleteMapping("/batch")
  public Result<String> batchDelete(@Valid @RequestBody BatchDeleteSCDTO batchDeleteDTO) {
    int count = scService.batchDelete(batchDeleteDTO);
    return Result.success("成功删除 " + count + " 条记录");
  }
}
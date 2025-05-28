package demo.ch7_5.controller;

import demo.ch7_5.common.Result;
import demo.ch7_5.entity.dto.ImportResultDTO;
import demo.ch7_5.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ImportController {
  @Autowired
  private ImportService importService;

  /**
   * 导入学生数据
   */
  @PostMapping("/students/import")
  public Result<?> importStudents(@RequestParam("file") MultipartFile file) {
    try {
      ImportResultDTO result = importService.importStudents(file);
      return Result.success(result);
    } catch (Exception e) {
      return Result.failed("导入失败：" + e.getMessage());
    }
  }

  /**
   * 导入课程数据
   */
  @PostMapping("/courses/import")
  public Result<?> importCourses(@RequestParam("file") MultipartFile file) {
    try {
      ImportResultDTO result = importService.importCourses(file);
      return Result.success(result);
    } catch (Exception e) {
      return Result.failed("导入失败：" + e.getMessage());
    }
  }

  /**
   * 导入选课数据
   */
  @PostMapping("/sc/import")
  public Result<?> importSC(@RequestParam("file") MultipartFile file) {
    try {
      ImportResultDTO result = importService.importSC(file);
      return Result.success(result);
    } catch (Exception e) {
      return Result.failed("导入失败：" + e.getMessage());
    }
  }
}
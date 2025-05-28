package demo.ch7_5.service;

import demo.ch7_5.entity.dto.ImportResultDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ImportService {
  /**
   * 导入学生数据
   *
   * @param file CSV文件
   * @return 导入结果
   */
  ImportResultDTO importStudents(MultipartFile file) throws IOException;

  /**
   * 导入课程数据
   *
   * @param file CSV文件
   * @return 导入结果
   */
  ImportResultDTO importCourses(MultipartFile file) throws IOException;

  /**
   * 导入选课数据
   *
   * @param file CSV文件
   * @return 导入结果
   */
  ImportResultDTO importSC(MultipartFile file) throws IOException;
}
package demo.ch7_5.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import demo.ch7_5.entity.Course;
import demo.ch7_5.entity.SC;
import demo.ch7_5.entity.Student;
import demo.ch7_5.entity.dto.ImportResultDTO;
import demo.ch7_5.service.CourseService;
import demo.ch7_5.service.ImportService;
import demo.ch7_5.service.SCService;
import demo.ch7_5.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Service
public class ImportServiceImpl implements ImportService {
  @Autowired
  private StudentService studentService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private SCService scService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ImportResultDTO importStudents(MultipartFile file) throws IOException {
    ImportResultDTO result = new ImportResultDTO();
    result.setErrors(new ArrayList<>());

    try (CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())))) {
      // 跳过表头
      reader.readNext();

      String[] line;
      while ((line = reader.readNext()) != null) {
        try {
          if (line.length < 5) {
            result.getErrors().add("数据格式不正确：" + String.join(",", line));
            result.incrementFailure();
            continue;
          }

          // 打印每个字段的值，用于调试
          System.out.println("学号: [" + line[0] + "]");
          System.out.println("姓名: [" + line[1] + "]");
          System.out.println("性别: [" + line[2] + "]");
          System.out.println("年龄: [" + line[3] + "]");
          System.out.println("系部: [" + line[4] + "]");

          Student student = new Student();
          student.setSno(line[0].trim());
          student.setSname(line[1].trim());
          student.setSsex(line[2].trim());
          student.setSdept(line[4].trim());

          String ageStr = line[3].trim();
          System.out.println("处理前的年龄字符串: [" + ageStr + "]");
          try {
            int age = Integer.parseInt(ageStr);
            System.out.println("转换后的年龄数值: " + age);
            student.setSage(age);
          } catch (NumberFormatException e) {
            System.out.println("年龄转换失败: " + e.getMessage());
            result.getErrors().add("年龄格式不正确：" + ageStr);
            result.incrementFailure();
            continue;
          }

          if (studentService.getById(student.getSno()) != null) {
            result.getErrors().add("学号已存在：" + student.getSno());
            result.incrementFailure();
            continue;
          }

          // 打印最终要保存的学生对象
          System.out.println("准备保存的学生对象: " + student);

          studentService.save(student);
          result.incrementSuccess();
        } catch (Exception e) {
          System.out.println("处理数据时发生异常: " + e.getMessage());
          e.printStackTrace();
          result.getErrors().add("处理数据失败：" + e.getMessage());
          result.incrementFailure();
        }
      }
    } catch (CsvValidationException e) {
      System.out.println("CSV验证失败: " + e.getMessage());
      throw new IOException("CSV文件格式错误", e);
    }

    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ImportResultDTO importCourses(MultipartFile file) throws IOException {
    ImportResultDTO result = new ImportResultDTO();
    result.setErrors(new ArrayList<>());

    try (CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())))) {
      reader.readNext(); // 跳过表头

      String[] line;
      while ((line = reader.readNext()) != null) {
        try {
          if (line.length < 4) {
            result.getErrors().add("数据格式不正确：" + String.join(",", line));
            result.incrementFailure();
            continue;
          }

          // 打印每个字段的值，用于调试
          System.out.println("课程号: [" + line[0] + "]");
          System.out.println("课程名: [" + line[1] + "]");
          System.out.println("学分: [" + line[2] + "]");
          System.out.println("系部: [" + line[3] + "]");

          Course course = new Course();
          course.setCno(line[0].trim());
          course.setCname(line[1].trim());

          // 处理学分字段
          try {
            int credit = Integer.parseInt(line[2].trim());
            course.setCredit(credit);
          } catch (NumberFormatException e) {
            System.out.println("学分转换失败: " + e.getMessage());
            result.getErrors().add("学分格式不正确：" + line[2]);
            result.incrementFailure();
            continue;
          }

          course.setCdept(line[3].trim());

          // 打印最终要保存的课程对象
          System.out.println("准备保存的课程对象: " + course);

          if (courseService.getById(course.getCno()) != null) {
            result.getErrors().add("课程号已存在：" + course.getCno());
            result.incrementFailure();
            continue;
          }

          courseService.save(course);
          result.incrementSuccess();
        } catch (Exception e) {
          result.getErrors().add("处理数据失败：" + e.getMessage());
          result.incrementFailure();
        }
      }
    } catch (CsvValidationException e) {
      throw new IOException("CSV文件格式错误", e);
    }

    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ImportResultDTO importSC(MultipartFile file) throws IOException {
    ImportResultDTO result = new ImportResultDTO();
    result.setErrors(new ArrayList<>());

    try (CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())))) {
      reader.readNext(); // 跳过表头

      String[] line;
      while ((line = reader.readNext()) != null) {
        try {
          if (line.length < 3) {
            result.getErrors().add("数据格式不正确：" + String.join(",", line));
            result.incrementFailure();
            continue;
          }

          // 打印每个字段的值，用于调试
          System.out.println("学号: [" + line[0] + "]");
          System.out.println("课程号: [" + line[1] + "]");
          System.out.println("成绩: [" + (line.length > 2 ? line[2] : "空") + "]");

          String sno = line[0].trim();
          String cno = line[1].trim();

          // 处理成绩字段，允许为空
          Double grade = null;
          if (line.length > 2 && line[2] != null && !line[2].trim().isEmpty()) {
            try {
              grade = Double.parseDouble(line[2].trim());
              System.out.println("转换后的成绩数值: " + grade);

              // 验证成绩范围（0-100）
              if (grade < 0 || grade > 100) {
                result.getErrors().add("成绩必须在0-100之间：" + grade);
                result.incrementFailure();
                continue;
              }
            } catch (NumberFormatException e) {
              System.out.println("成绩转换失败: " + e.getMessage());
              result.getErrors().add("成绩格式不正确：" + line[2]);
              result.incrementFailure();
              continue;
            }
          } else {
            System.out.println("成绩为空，将在后续更新");
          }

          // 验证学生是否存在
          if (studentService.getById(sno) == null) {
            System.out.println("学号不存在: " + sno);
            result.getErrors().add("学号不存在：" + sno);
            result.incrementFailure();
            continue;
          }

          // 验证课程是否存在
          if (courseService.getById(cno) == null) {
            System.out.println("课程号不存在: " + cno);
            result.getErrors().add("课程号不存在：" + cno);
            result.incrementFailure();
            continue;
          }

          SC sc = new SC();
          sc.setSno(sno);
          sc.setCno(cno);
          if (grade != null) {
            sc.setGrade(grade);
          }

          // 打印最终要保存的选课对象
          System.out.println("准备保存的选课对象: " + sc);

          scService.save(sc);
          result.incrementSuccess();
        } catch (Exception e) {
          System.out.println("处理数据时发生异常: " + e.getMessage());
          e.printStackTrace();
          result.getErrors().add("处理数据失败：" + e.getMessage());
          result.incrementFailure();
        }
      }
    } catch (CsvValidationException e) {
      System.out.println("CSV验证失败: " + e.getMessage());
      throw new IOException("CSV文件格式错误", e);
    }

    return result;
  }
}
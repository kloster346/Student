package demo.ch7_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.ch7_5.entity.Student;

import java.util.List;

/**
 * 学生Service接口
 */
public interface StudentService extends IService<Student> {
  /**
   * 批量删除学生
   *
   * @param ids 学生ID列表
   * @return 删除的记录数
   */
  int deleteBatch(List<String> ids);
}
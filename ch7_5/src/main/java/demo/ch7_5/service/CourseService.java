package demo.ch7_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.ch7_5.entity.Course;
import demo.ch7_5.entity.dto.BatchDeleteDTO;

/**
 * 课程服务接口
 */
public interface CourseService extends IService<Course> {
  /**
   * 批量删除课程
   *
   * @param batchDeleteDTO 批量删除请求
   * @return 删除的记录数
   */
  int batchDelete(BatchDeleteDTO batchDeleteDTO);
}
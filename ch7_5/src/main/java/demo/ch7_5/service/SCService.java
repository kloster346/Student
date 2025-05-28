package demo.ch7_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.ch7_5.entity.SC;
import demo.ch7_5.entity.dto.BatchDeleteSCDTO;

public interface SCService extends IService<SC> {
  // 继承IService后即可获得常用的CRUD方法
  // 如需要自定义业务方法可在此添加

  /**
   * 批量删除选课记录
   *
   * @param batchDeleteDTO 批量删除请求
   * @return 删除的记录数
   */
  int batchDelete(BatchDeleteSCDTO batchDeleteDTO);
}
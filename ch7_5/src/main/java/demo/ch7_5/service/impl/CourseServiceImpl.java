package demo.ch7_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.ch7_5.entity.Course;
import demo.ch7_5.entity.dto.BatchDeleteDTO;
import demo.ch7_5.mapper.CourseMapper;
import demo.ch7_5.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 课程服务实现类
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
  // 继承ServiceImpl后即可获得常用的CRUD方法实现
  // 如需要自定义业务方法实现可在此添加

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int batchDelete(BatchDeleteDTO batchDeleteDTO) {
    return baseMapper.deleteBatchIds(batchDeleteDTO.getIds());
  }
}
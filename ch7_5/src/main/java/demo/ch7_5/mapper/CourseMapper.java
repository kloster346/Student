package demo.ch7_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.ch7_5.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMapper extends BaseMapper<Course> {
  // 继承BaseMapper后即可获得常用的CRUD方法
  // 如需要自定义复杂查询方法可在此添加
}
package demo.ch7_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.ch7_5.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程Mapper接口
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
  /**
   * 批量删除课程
   *
   * @param ids 课程ID列表
   * @return 删除的记录数
   */
  int deleteBatchIds(@Param("ids") List<String> ids);
}
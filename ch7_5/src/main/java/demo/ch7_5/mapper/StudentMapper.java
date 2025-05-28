package demo.ch7_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.ch7_5.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper接口
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
  /**
   * 批量删除学生
   *
   * @param ids 学生ID列表
   * @return 删除的记录数
   */
  int deleteBatchIds(@Param("ids") List<String> ids);

  // 继承BaseMapper后即可获得常用的CRUD方法
  // 如需要自定义复杂查询方法可在此添加
}
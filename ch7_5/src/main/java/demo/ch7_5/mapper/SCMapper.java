package demo.ch7_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.ch7_5.entity.SC;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SCMapper extends BaseMapper<SC> {
  /**
   * 批量删除选课记录
   *
   * @param snos 学号列表
   * @param cnos 课程号列表
   * @return 删除的记录数
   */
  int batchDelete(@Param("snos") List<String> snos, @Param("cnos") List<String> cnos);
}
package demo.ch7_5.common;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 分页结果
 */
@Data
public class PageResult<T> {
  /**
   * 总记录数
   */
  private Long total;

  /**
   * 数据列表
   */
  private List<T> list;

  public PageResult(Long total, List<T> list) {
    this.total = total;
    this.list = list;
  }

  /**
   * 创建空结果
   */
  public static <T> PageResult<T> empty() {
    return new PageResult<>(0L, Collections.emptyList());
  }
}
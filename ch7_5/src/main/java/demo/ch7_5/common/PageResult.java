package demo.ch7_5.common;

import lombok.Getter;

@Getter
public class PageResult<T> {
  private long total;
  private java.util.List<T> list;

  public PageResult(long total, java.util.List<T> list) {
    this.total = total;
    this.list = list;
  }
}
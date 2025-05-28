package demo.ch7_5.entity.dto;

import lombok.Data;
import java.util.List;

@Data
public class ImportResultDTO {
  private int totalCount; // 总记录数
  private int successCount; // 成功导入数
  private int failureCount; // 失败数
  private List<String> errors; // 错误信息列表

  public ImportResultDTO() {
    this.totalCount = 0;
    this.successCount = 0;
    this.failureCount = 0;
  }

  public void incrementSuccess() {
    this.successCount++;
    this.totalCount++;
  }

  public void incrementFailure() {
    this.failureCount++;
    this.totalCount++;
  }
}
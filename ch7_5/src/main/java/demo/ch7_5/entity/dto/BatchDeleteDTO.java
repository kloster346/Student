package demo.ch7_5.entity.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 批量删除请求DTO
 */
@Data
public class BatchDeleteDTO {
  /**
   * 要删除的ID列表
   */
  @NotEmpty(message = "删除的ID列表不能为空")
  private List<String> ids;
}
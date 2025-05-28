package demo.ch7_5.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class BatchDeleteSCDTO {
  @NotEmpty(message = "学号列表不能为空")
  private List<String> snos;

  @NotEmpty(message = "课程号列表不能为空")
  private List<String> cnos;
}
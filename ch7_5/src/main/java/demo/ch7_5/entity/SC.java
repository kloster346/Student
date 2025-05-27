package demo.ch7_5.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("sc")
public class SC extends Model<SC> {
  @TableId(value = "sno")
  private String sno; // 学号
  private String cno; // 课程号
  private Double grade; // 成绩
}
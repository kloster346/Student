package demo.ch7_5.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("student")
public class Student extends Model<Student> {
  @TableId(value = "sno")
  private String sno; // 学号
  private String sname; // 姓名
  private String ssex; // 性别
  private Integer sage; // 年龄
  private String sdept; // 院系
}
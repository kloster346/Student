package demo.ch7_5.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("course")
public class Course extends Model<Course> {
  @TableId(value = "cno")
  private String cno; // 课程号
  private String cname; // 课程名
  private Integer credit; // 学分
  private String cdept; // 开课院系
}
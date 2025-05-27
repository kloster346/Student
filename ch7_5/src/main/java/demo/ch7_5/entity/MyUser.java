package demo.ch7_5.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("user")
public class MyUser extends Model<MyUser> {//ActiveRecord模式
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;//与数据库中的字段名相同
    private String uname;
    private String usex;
}

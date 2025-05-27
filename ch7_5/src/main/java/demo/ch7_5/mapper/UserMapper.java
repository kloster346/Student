package demo.ch7_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.ch7_5.entity.MyUser;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<MyUser> {

}
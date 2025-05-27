package demo.ch7_5.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.ch7_5.entity.MyUser;
import demo.ch7_5.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, MyUser> implements UserService {

}

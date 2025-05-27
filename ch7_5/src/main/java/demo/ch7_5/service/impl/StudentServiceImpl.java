package demo.ch7_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.ch7_5.entity.Student;
import demo.ch7_5.mapper.StudentMapper;
import demo.ch7_5.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
  // 继承ServiceImpl后即可获得常用的CRUD方法实现
  // 如需要自定义业务方法实现可在此添加
}
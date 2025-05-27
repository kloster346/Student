package demo.ch7_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.ch7_5.entity.Student;

public interface StudentService extends IService<Student> {
  // 继承IService后即可获得常用的CRUD方法
  // 如需要自定义业务方法可在此添加
}
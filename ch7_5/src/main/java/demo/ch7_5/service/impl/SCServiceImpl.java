package demo.ch7_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.ch7_5.entity.SC;
import demo.ch7_5.mapper.SCMapper;
import demo.ch7_5.service.SCService;
import org.springframework.stereotype.Service;

@Service
public class SCServiceImpl extends ServiceImpl<SCMapper, SC> implements SCService {
  // 继承ServiceImpl后即可获得常用的CRUD方法实现
  // 如需要自定义业务方法实现可在此添加
}
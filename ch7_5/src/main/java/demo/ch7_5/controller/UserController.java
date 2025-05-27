package demo.ch7_5.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.ch7_5.entity.PageReq;
import demo.ch7_5.entity.MyUser;
import demo.ch7_5.mapper.UserMapper;
import demo.ch7_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Arrays;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<MyUser> getAll() {
        return userMapper.selectList(null);
    }

    @GetMapping("/listAll")
    public List<MyUser> listAll() {
        return userService.list();
    }

    @GetMapping("/testMapperSave")
    public MyUser testMapperSave() {
        MyUser myUser=new MyUser();
        myUser.setUname("testMapperSave陈恒1");
        myUser.setUsex("女");
        int result = userMapper.insert(myUser);
        //实体类主键属性使用@TableId注解后，主键自动回填
        return myUser;
    }

    @GetMapping("/testMapperDelete")
    public int testMapperDelete() {
        List<Long> list = Arrays.asList(2009L, 2011L, 2013L);
        int result = userMapper.deleteBatchIds(list);
        return result;
    }

    @GetMapping("/testMapperUpdate")
    public MyUser testMapperUpdate() {
        MyUser myUser=new MyUser();
        myUser.setUid(2006);
        myUser.setUname("李四");
        myUser.setUsex("男");
        int result = userMapper.updateById(myUser);
        return myUser;
    }

    @GetMapping("/testServiceSave")
    public List<MyUser> testServiceSave() {
        System.out.println("testServiceSave-----");
        MyUser myUser1=new MyUser();
        myUser1.setUname("testServiceSave陈恒2");
        myUser1.setUsex("女");
        MyUser myUser2=new MyUser();
        myUser2.setUname("testServiceSave陈恒3");
        myUser2.setUsex("男");
        List<MyUser> list=Arrays.asList(myUser1,myUser2);
        boolean result = userService.saveBatch(list);
        return list;
    }

    @GetMapping("/testServiceUpdate")
    public List<MyUser> testServiceUpdate() {
        MyUser mu1=new MyUser();
        mu1.setUid(2006);
        mu1.setUname("Update陈恒11");
        mu1.setUsex("女");
        MyUser mu2=new MyUser();
        mu2.setUid(2006);
        mu2.setUname("Update陈恒22");
        mu2.setUsex("男");
        List<MyUser> list=Arrays.asList(mu1,mu2);
        boolean result = userService.updateBatchById(list);
        return list;
    }

    @PostMapping("/getUserPage")
    public Page<MyUser> getUserPage(@RequestBody PageReq pageReq) {
        //这两句输出语句用于测试参数是否正常获取
        System.out.println("===" + pageReq.getCurrent());
        System.out.println("==== " + pageReq.getSize());
        //
        Page<MyUser> page = new Page<>(pageReq.getCurrent(), pageReq.getSize());
        //
         //
        return userMapper.selectPage(page, null);
    }

    @GetMapping("/testQueryWrapper")
    public List<MyUser> testQueryWrapper() {
        QueryWrapper<MyUser> queryWrapper = new QueryWrapper<MyUser>();
        queryWrapper.like("uname","陈")
                    .eq("usex","男");
                return userService.list(queryWrapper);
    }

    @GetMapping("/testServiceQuery")
    public List<MyUser> testServiceQuery() {
        return userService
                .query()
                .like("uname","陈")
                .eq("usex","男")
                .list();
    }

    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody MyUser myUser) {
        System.out.println(myUser.toString());
        return userService.updateById(myUser);
    }

    @PostMapping("/deleteUser")
    public boolean deleteUser(@RequestBody Integer uid) {
        System.out.println("uid="+uid);
        return userService.removeById(uid);
    }



}

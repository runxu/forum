package com.markerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.markerhub.common.dto.LoginDto;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import com.markerhub.util.JwtUtils;
import com.markerhub.util.Md5Utils;
import com.markerhub.util.UuidUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");

        //不为空再判断密码是否正确
        String md5 = Md5Utils.getMd5(loginDto.getPassword() + user.getSalt());
        //判断加密后的密码与数据库中密码是否一致
        if (!md5.equalsIgnoreCase(user.getPassword())) {
            return Result.fail("密码错误");
        }


        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);

        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("birthday",user.getBirthday())
                .put("area",user.getArea())
                .map()
        );


    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }


    @SneakyThrows
    @PostMapping("/register")
    public Result register(@Validated @RequestBody Map<String, Object> map){

        User user = new User();
        Map<String, String> userMap = (Map<String, String>)map.get("user");
        user.setUsername(userMap.get("username"));
        user.setPhone(userMap.get("phone"));
        user.setPassword(userMap.get("password"));
        user.setEmail(userMap.get("email"));

        String code = (String)map.get("code");
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        String smsCode = ops.get("smsCode_"+user.getPhone());

        if(!code.equals(smsCode)){
            return Result.fail("验证码错误");
        }
        //4.对密码使用md5加密：md5(用户名+密码+盐)
        //产生盐
        String salt = UuidUtils.simpleUuid();
        //使用md5进行加密
        String md5 = Md5Utils.getMd5(user.getPassword() + salt);
        //替换原来密码字段
        user.setPassword(md5);
        //设置盐
        user.setSalt(salt);
        user.setStatus(0);

        //5.调用dao添加用户方法
        boolean save = userService.save(user);
        if(save == true){
            return Result.succ("注册成功");
        }else{
            return Result.fail("注册失败");
        }


    }

    @PostMapping("/sendSms")
    public Result sendSms(String phone){

        if(phone.length() != 11){
            return Result.fail("错误的手机号");
        }
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println("验证码"+code);
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        ops.set("smsCode_" + phone, code, 60L, TimeUnit.SECONDS);

        return Result.succ("发送成功");
    }

    @RequiresAuthentication
    @RequestMapping("/updateUserImg")
    public Result updateUserImg(@RequestParam(value = "user",required = false) String user,
                                      @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        User u = JSON.parseObject(user, User.class);

        String name = file.getOriginalFilename();
        InputStream in = file.getInputStream();
        File mkdir = new File("/files/images/");
        if (!mkdir.exists()) {
            mkdir.mkdirs();
        }
        FileOutputStream os = new FileOutputStream(mkdir.getPath() + "/" + u.getUsername()+".jpg");
        int len = 0;
        while((len = in.read()) != -1) {
            os.write(len);
        }
        os.flush();
        in.close();
        os.close();
        u.setAvatar(u.getUsername()+".jpg");
        Long userid = u.getId();
        //return null;
        boolean update = userService.update((Wrapper) ((UpdateWrapper) (new UpdateWrapper()).eq("id", userid)).set("avatar", u.getAvatar()));
        if (update == true) {
            return Result.succ(200,"修改头像成功",MapUtil.builder()
                    .put("id", u.getId())
                    .put("username", u.getUsername())
                    .put("avatar", u.getAvatar())
                    .put("email", u.getEmail())
                    .map()
            );
        } else {
            return Result.fail("修改头像失败");
        }
    }

    @RequiresAuthentication
    @PostMapping("/updateUser")
    public Result updateUser(@RequestParam(value = "userid",required = false)String id,
                             @RequestParam(value = "username",required = false)String username,
                             @RequestParam(value = "birthday",required = false)String birthday,
                             @RequestParam(value = "area",required = false)String area){

        boolean update = userService.update((Wrapper) ((UpdateWrapper) (new UpdateWrapper()).eq("id", id)).set("username", username));
        if(update == false){
            return Result.fail("修改用户名失败");
        }
        boolean update1 = userService.update((Wrapper) ((UpdateWrapper) (new UpdateWrapper()).eq("id", id)).set("birthday", birthday));
        if(update1 == false){
            return Result.fail("修改生日失败");
        }
        boolean update2 = userService.update((Wrapper) ((UpdateWrapper) (new UpdateWrapper()).eq("id", id)).set("area", area));
        if(update2 == false){
            return Result.fail("修改地址失败");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("id", id));
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("birthday",user.getBirthday())
                .put("area",user.getArea())
                .map());
    }

    @RequiresAuthentication
    @PostMapping("/sendPasswordSms")
    public Result sendPasswordSms(@RequestParam(value = "userid") String id,@RequestParam(value = "phone") String phone){

        User u = userService.getOne(new QueryWrapper<User>().eq("id", id));
        if(!u.getPhone().equals(phone)){
            return Result.fail("手机与账户不匹配");
        }
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println("密保验证码"+code);
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        ops.set("smsPasswordCode_" + phone, code, 60L, TimeUnit.SECONDS);

        return Result.succ("发送成功");
    }

    @RequiresAuthentication
    @PostMapping("/updateUserPassword")
    public Result updateUserPassword(@RequestParam(value = "userid") String id,
                                     @RequestParam(value = "oldpassword") String oldpassword,
                                     @RequestParam(value = "newpassword")String newpassword,
                                     @RequestParam(value = "phone")String phone,
                                     @RequestParam(value = "code")String code){
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        String smsCode = ops.get("smsPasswordCode_"+phone);

        if(!code.equals(smsCode)){
            return Result.fail("验证码错误");
        }
        User u = userService.getOne(new QueryWrapper<User>().eq("id", id));
        String md5 = Md5Utils.getMd5(oldpassword + u.getSalt());
        if(!u.getPassword().equals(md5)){
            return Result.fail("密码错误");
        }
        String nowpassword = Md5Utils.getMd5(newpassword + u.getSalt());
        boolean update = userService.update((Wrapper) ((UpdateWrapper) (new UpdateWrapper()).eq("id", id)).set("password", nowpassword));
        if(update == false){
            return Result.fail("修改失败");
        }

        return Result.succ("修改成功");
    }



    @RequiresAuthentication
    @PostMapping("/validphone")
    public Result validphone(@RequestParam(value = "phone")String phone,
                             @RequestParam(value = "code")String code){
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        String smsCode = ops.get("smsPasswordCode_"+phone);

        if(!code.equals(smsCode)){
            return Result.fail("输入的验证码或手机有误");
        }

        return Result.succ("验证通过");
    }

    @RequiresAuthentication
    @PostMapping("/updatephonesms")
    public Result updatephonesms(@RequestParam(value = "userid") String id,@RequestParam(value = "phone") String phone){

        User u = userService.getOne(new QueryWrapper<User>().eq("id", id));
        if(u.getPhone().equals(phone)){
            return Result.fail("新密保手机不能与近期设置的相同");
        }

        String code = RandomStringUtils.randomNumeric(6);
        System.out.println("新密保验证码"+code);
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        ops.set("updatephonesms_" + phone, code, 60L, TimeUnit.SECONDS);

        return Result.succ("发送成功");
    }

    @RequiresAuthentication
    @PostMapping("/updatephone")
    public Result updatephone(@RequestParam(value = "userid") String id,
                              @RequestParam(value = "phone")String phone,
                              @RequestParam(value = "code")String code){

        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        String smsCode = ops.get("updatephonesms_"+phone);

        if(!smsCode.equals(code)){
            return Result.fail("输入的验证码或手机有误");
        }
        boolean update = userService.update(new UpdateWrapper<User>().eq("id", id).set("phone", phone));
        if(update == false){
            return Result.fail("修改失败");
        }
        return Result.succ("修改成功");
    }

}

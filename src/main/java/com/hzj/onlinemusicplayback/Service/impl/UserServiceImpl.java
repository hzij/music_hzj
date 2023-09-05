package com.hzj.onlinemusicplayback.Service.impl;


import com.hzj.onlinemusicplayback.Mapper.UserMapper;
import com.hzj.onlinemusicplayback.Service.UserService;
import com.hzj.onlinemusicplayback.pojo.User;
import com.hzj.onlinemusicplayback.utils.Constant;
import com.hzj.onlinemusicplayback.utils.ResponseBodyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * PackageName :com.hzj.Service.impl
 * ClassName: UserServiceImpl
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/4  17:14
 * @edition 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseBodyMessage<User> login(String username, String password, HttpServletRequest request) {
        User user = new User();
        user.setUsername(username);
        //密码的BCrypt加密
        User userLogin = userMapper.login(username);

        //用户名存在校验
        if (userLogin.getUsername() == null){
           return new ResponseBodyMessage<>(-1,"用户名或密码错误",userLogin);
        }
        //密码校验
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(password);
        boolean matches = bCryptPasswordEncoder.matches(password, encode);
        if (!matches){
           return new ResponseBodyMessage<>(-1,"密码错误",userLogin);
        }else {
            request.getSession().setAttribute(Constant.USERINFO_SESSION_KEY,userLogin);
            return new ResponseBodyMessage<>(0,"登录成功",userLogin);
        }
    }
}

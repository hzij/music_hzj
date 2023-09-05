package com.hzj.onlinemusicplayback.Controller;

import com.hzj.Mapper.UserMapper;
import com.hzj.Service.UserService;
import com.hzj.pojo.User;
import com.hzj.utils.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * PackageName :com.hzj.Controller
 * ClassName: UserController
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/4  17:11
 * @edition 1.0
 */
@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("login")
    public ResponseBodyMessage<User> login(@RequestParam String username,@RequestParam String password,HttpServletRequest request){
        ResponseBodyMessage<User> userLoginMessage = userService.login(username, password,request);
        return userLoginMessage;
    }
}

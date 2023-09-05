package com.hzj.onlinemusicplayback.Service;

import com.hzj.onlinemusicplayback.pojo.User;
import com.hzj.onlinemusicplayback.utils.ResponseBodyMessage;


import javax.servlet.http.HttpServletRequest;

/**
 * PackageName :com.hzj.Service
 * ClassName: UserService
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/4  17:13
 * @edition 1.0
 */
public interface UserService {
    /**
     * 登录验证
     */
    ResponseBodyMessage<User> login(String username, String password, HttpServletRequest request);
}

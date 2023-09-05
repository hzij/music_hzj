package com.hzj.onlinemusicplayback.config;

import com.hzj.onlinemusicplayback.utils.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * PackageName :com.hzj.onlinemusicplayback.config
 * ClassName: LoginInterceptor
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/5  13:09
 * @edition 1.0
 */
@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    //在controller执行之前执行，这里是用来进行登录验证
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        Object attribute = request.getSession().getAttribute(Constant.USERINFO_SESSION_KEY);
        if (session!=null || attribute!=null){
            //登录成功
            return true;
        }
        return false;
    }
}

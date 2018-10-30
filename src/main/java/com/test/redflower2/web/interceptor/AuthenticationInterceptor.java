package com.test.redflower2.web.interceptor;

import com.test.redflower2.exception.NoAuthenticationException;
import com.test.redflower2.exception.NotLoginException;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * 登录拦截器
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private UserService userService;
    @Autowired
    public AuthenticationInterceptor(UserService userService){
        this.userService=userService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnsupportedEncodingException, NotLoginException ,NoAuthenticationException{
        HttpSession session = request.getSession();

        //用拦截器解决请求乱码问题
        request.setCharacterEncoding("utf-8");

        Integer userId = (Integer) session.getAttribute("userId");

        // 预检，若含有authorization，直接过
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        if (userId == null) {
            throw new NotLoginException("用户没有登录,请先登录！");
        }
        User user = userService.getUserById(userId);
        if (user==null){
            throw new NoAuthenticationException("No Authentication");
        }

//        request.setAttribute("userId", userId);

        return true;
    }
}

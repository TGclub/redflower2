package com.test.redflower2.web.interceptor;

import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.exception.NoAuthenticationException;
import com.test.redflower2.exception.NotLoginException;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger= LoggerFactory.getLogger(getClass());

    private UserService userService;
    @Autowired
    public AuthenticationInterceptor(UserService userService){
        this.userService=userService;
    }
    @Override
    //该方法是请求前的一个预处理，只有返回true时，后面两个方法才会执行
    //前置初始化操作或者是对当前请求的一个预处理
    //可以在这个方法中进行一些判断来决定请求是否要继续进行下去
    //当返回值为true 时就会继续调用下一个Interceptor 的preHandle 方法，
    // 如果已经是最后一个Interceptor 的时候就会是调用当前请求的Controller 方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnsupportedEncodingException, NotLoginException ,NoAuthenticationException{
        logger.info(request+" time "+System.currentTimeMillis());
        HttpSession session = request.getSession();
        //用拦截器解决请求乱码问题
        request.setCharacterEncoding("utf-8");
        Integer userId = (Integer) session.getAttribute(UserConstant.USER_ID);
        // 预检，若含有authorization，直接过
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {//忽略大小写
            return true;
        }
        if (userId == null) {
            throw new NotLoginException("用户没有登录,请先登录！");
        }
        User user = userService.getUserById(userId);
        if (user==null){
            throw new NoAuthenticationException("No Authentication");
        }
        return true;
    }
}

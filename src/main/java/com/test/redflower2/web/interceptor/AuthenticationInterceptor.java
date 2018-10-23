package com.test.redflower2.web.interceptor;

import com.test.redflower2.exception.NotLoginException;
import org.springframework.stereotype.Component;
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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnsupportedEncodingException, NotLoginException {
        HttpSession session = request.getSession();

        //用拦截器解决请求乱码问题
        request.setCharacterEncoding("utf-8");

        String userId = (String) session.getAttribute("userId");

        // 预检，若含有authorization，直接过
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        if (userId == null) {
            throw new NotLoginException();
        }

        request.setAttribute("userId", userId);

        return true;
    }
}

package com.test.redflower2.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 控制器基类
 */
@RestController
public class BaseController {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private HttpSession session;

}

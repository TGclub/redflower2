package com.test.redflower2.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.redflower2.annotation.Authorization;
import com.test.redflower2.constant.NetworkConstant;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.pojo.dto.Result;
import com.test.redflower2.pojo.dto.ResultBuilder;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.NetworkService;
import com.test.redflower2.service.UserService;
import com.test.redflower2.utils.ObjectUtil;
import com.test.redflower2.utils.WechatUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.uid;


/**
 * 我的页面和登录页面接口
 */

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    public static  int sum =0;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private NetworkService networkService;

    private UserService userService;

    private WechatUtil wechatUtil;

    @Autowired
    public UserController(UserService userService, WechatUtil wechatUtil,NetworkService networkService) {
        this.userService = userService;
        this.wechatUtil = wechatUtil;
        this.networkService=networkService;
    }

    /**
     * ok
     * 首页登录
     * 用户微信认证登录
     *
     * @param json
     * @param session
     * @return
     * @throws Exception
     */
    @ApiOperation(value = UserConstant.USER_LOGIN_DESC, httpMethod = "POST")
    @PostMapping("/login")
    public Result<Object> login(@RequestBody(required = false) String json,
                                HttpSession session) throws Exception {
        logger.info("code:" + json + " time " + System.currentTimeMillis());

        if (ObjectUtil.isStringEmpty(json)) {
            return ResultBuilder.fail("传进来参数null");
        }
        //解析相应内容,(转换成json对象)
        String code;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            code = jsonObject.getString("code");
        } catch (Exception e) {
            System.out.println("code错误:");
            return ResultBuilder.fail(UserConstant.OPENID_NULL);
        }
        //获取code
        if (ObjectUtil.isStringEmpty(code)) {
            return ResultBuilder.fail(UserConstant.OPENID_NULL);
        }
        String openId = wechatUtil.getOpenId(code);
        if (ObjectUtil.isStringEmpty(openId)){
            return ResultBuilder.fail("openId为空!");
        }
        User user = userService.isLoginSuccess1(openId);
        //登录成功
        session.setAttribute(UserConstant.USER_ID,user.getId());

        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        //如果没有创建
        boolean result = networkService.isNotCreateThreeCircle(uid);
        if (result){
            networkService.createThreeCircle(uid);
        }
        return ResultBuilder.success(session.getId());
    }

    /**
     * 前端暂时没做这个功能
     * 我的页面
     * 修改昵称
     *
     * @param username
     * @return
     */
    @Authorization
    @ApiOperation(value = UserConstant.UPDATE_USERNAME, httpMethod = "PUT")
    @PutMapping("/updateUsername")
    public Result<Object> updateUsername(@RequestParam("username") String username,
                                         HttpSession session) {
        logger.info(username + " time " + System.currentTimeMillis());
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        String result = userService.updateName(uid, username);
        //修改成功
        if (result.equals(UserConstant.SUCCESS_MSG)) {
            return ResultBuilder.success();
        } else {
            return ResultBuilder.fail(result);
        }
    }

    /**
     * 前端暂时没做这个功能
     * 我的页面
     * 用户修改自定义个性签名
     *
     * @param definition
     * @param session
     * @return
     */
    @Authorization
    @ApiOperation(value = UserConstant.UPDATE_DEFINITION, httpMethod = "PUT")
    @PutMapping("/updateDefinition")
    public Result<Object> updateDefinition(@RequestParam("definition") String definition,
                                           HttpSession session) {
        logger.info("updateDefinition: "+definition + " time " + System.currentTimeMillis());
        String result = userService.updateDefinition(definition, session);
        if (result.equals(UserConstant.SUCCESS_MSG)) {
            return ResultBuilder.success();
        } else {
            return ResultBuilder.fail(result);
        }
    }


    /**
     * good
     * 我的页面
     * 返回登录后用户的信息
     *
     * @param session
     * @return
     */
    @Authorization
    @ApiOperation(value = UserConstant.GET_USER_INFO, httpMethod = "GET")
    @GetMapping("/userInfo")
    public Result<User> getUserInfo(HttpSession session) {
        System.out.println(session.getSessionContext());
        logger.info("返回登录后用户的信息 :" + System.currentTimeMillis());
        Integer userId = (Integer) session.getAttribute(UserConstant.USER_ID);
        User user = userService.getUserById(userId);
        return ResultBuilder.success(user);
    }

    /**
     * 前端暂时没做这个功能
     * 我的页面
     * 用户退出
     *
     * @param session
     * @return
     */
    @Authorization
    @ApiOperation(value = UserConstant.USER_LOGOUT, httpMethod = "GET")
    @GetMapping("/logout")
    public Result<Object> logout(HttpSession session) {
        logger.info("用户退出 :" + System.currentTimeMillis());
        session.invalidate();
        return ResultBuilder.success();
    }

    /**
     * 测试
     *
     * @return
     */
    @Authorization
    @GetMapping("/test")
    public Result<Object> test() {
        logger.info("test :" + System.currentTimeMillis());
        return ResultBuilder.success();
    }


    /**
     * good
     * 我的页面
     * 更新用户信息
     *
     * @param user
     * @param session
     * @return
     */
    @PutMapping("/updateUserInfo")
    public Result<Object> updateUserInfo(@RequestBody User user, HttpSession session) {
        logger.info("updateUserInfo :" + System.currentTimeMillis());
        String result = userService.updateUser(user, session);
        if (result.equals(UserConstant.SUCCESS_MSG)) {
            return ResultBuilder.success();
        } else {
            return ResultBuilder.fail(result);
        }
    }
}

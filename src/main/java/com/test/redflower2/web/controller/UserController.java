package com.test.redflower2.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.redflower2.annotation.Authorization;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.pojo.dto.Result;
import com.test.redflower2.pojo.dto.ResultBuilder;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.UserService;
import com.test.redflower2.utils.ObjectUtil;
import com.test.redflower2.utils.WechatUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserService userService;

    private WechatUtil wechatUtil;

    @Autowired
    public UserController(UserService userService, WechatUtil wechatUtil) {
        this.userService = userService;
        this.wechatUtil = wechatUtil;
    }

    /**
     * 用户微信认证登录
     * @param json
     * @param session
     * @return
     * @throws Exception
     */
    @ApiOperation(value = UserConstant.USER_LOGIN_DESC,httpMethod = "POST")
    @PostMapping("/login")
    public Result<Object> login(@RequestBody(required = false) String json,

                                HttpSession session)throws Exception{
        //解析相应内容,(转换成json对象)
        String code;
        JSONObject jsonObject= JSON.parseObject(json);
        code=jsonObject.getString("code");
        //获取code
//        String code = JsonUtil.JsonCode(jsonCode);
        String openId = wechatUtil.getOpenId(code);
        Map<Integer,String> datas = userService.isLoginSuccess(openId,session);
        //如果登录失败
        if (!ObjectUtil.isStringEmpty(datas.get(UserConstant.FAILED_CODE))){
            String status = datas.get(UserConstant.FAILED_CODE);
            return ResultBuilder.fail(status+"");
        }
        //登录成功
        return ResultBuilder.success(session.getId());
    }

    /**
     * 修改昵称
     * @param username
     * @return
     */
    @Authorization
    @ApiOperation(value = UserConstant.UPDATE_USERNAME,httpMethod = "PUT")
    @PutMapping("/updateUsername")
    public Result<Object> updateUsername(@RequestParam("username") String username,
                                             HttpSession session){
        logger.info(username+" time "+System.currentTimeMillis());
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        String result= userService.updateName(uid,username);
        //修改成功
        if (result.equals(UserConstant.SUCCESS_MSG)){
            return ResultBuilder.success();
        }else {
            return ResultBuilder.fail(result);
        }
    }

    /**
     * 用户修改自定义个性签名
     * @param definition
     * @param session
     * @return
     */
    @Authorization
    @ApiOperation(value = UserConstant.UPDATE_DEFINITION,httpMethod = "PUT")
    @PutMapping("/updateDefinition")
    public Result<Object> updateDefinition(@RequestParam("definition") String definition,
                                           HttpSession session){
        logger.info(definition+" time "+System.currentTimeMillis());
        String result = userService.updateDefinition(definition,session);
       if (result.equals(UserConstant.SUCCESS_MSG)){
           return ResultBuilder.success();
       }else {
           return ResultBuilder.fail(result);
       }
    }


    /**
     * 返回登录后用户的信息
     * @param session
     * @return
     */
    @Authorization
    @ApiOperation(value =UserConstant.GET_USER_INFO,httpMethod = "GET")
    @GetMapping("/userInfo")
    public Result<User> getUserInfo(HttpSession session)  {
        System.out.println(session.getSessionContext());
        logger.info("返回登录后用户的信息 :"+System.currentTimeMillis());
        Integer userId =  (Integer) session.getAttribute(UserConstant.USER_ID);
        User user = userService.getUserById(userId);
        return ResultBuilder.success(user);
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @Authorization
    @ApiOperation(value = UserConstant.USER_LOGOUT,httpMethod = "GET")
    @GetMapping("/logout")
    public Result<Object> logout(HttpSession session){
        logger.info("用户退出 :"+System.currentTimeMillis());
        session.invalidate();
        return ResultBuilder.success();
    }

    /**
     * 测试
     * @return
     */
    @Authorization
    @GetMapping("/test")
    public Result<Object> test(){
        logger.info("test :"+System.currentTimeMillis());
        return ResultBuilder.success();
    }


    /**
     * 更新用户信息
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/updateUserInfo")
    public Result<Object> updateUserInfo(@RequestBody User user, HttpSession session){
        String result = userService.updateUser(user,session);
        if (result.equals(UserConstant.SUCCESS_MSG)){
            return ResultBuilder.success();
        }else {
            return ResultBuilder.fail(result);
        }
    }
}

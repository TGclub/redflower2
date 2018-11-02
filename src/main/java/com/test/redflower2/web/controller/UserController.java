package com.test.redflower2.web.controller;

import com.test.redflower2.annotation.Authorization;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.pojo.dto.Result;
import com.test.redflower2.pojo.dto.ResultBuilder;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.UserService;
import com.test.redflower2.utils.ObjectUtil;
import com.test.redflower2.utils.WechatUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController{

    private UserService userService;

    private WechatUtil wechatUtil;

    @Autowired
    public UserController(UserService userService, WechatUtil wechatUtil) {
        this.userService = userService;
        this.wechatUtil = wechatUtil;
    }

    /**
     * 用户微信认证登录
     * @param code 前端给的code
     */
    @ApiOperation(value = UserConstant.USER_LOGIN_DESC,httpMethod = "POST")
    @PostMapping("/login")
    public Result<Object> login(@RequestParam(name = "code") String code,
                                HttpSession session)throws Exception{
        String openId = wechatUtil.getOpenId(code);
        if (openId == null) {
            return ResultBuilder.fail("code is wrong");
        }
        Map<String,Integer> datas = userService.isLoginSuccess(openId,session);
        //如果登录失败
        if (!ObjectUtil.isEmpty(datas.get(UserConstant.FAIL_MSG))){
            Integer status = datas.get(UserConstant.FAIL_MSG);
            return ResultBuilder.fail(status+"");
        }
        //登录成功
        return ResultBuilder.success();
    }

    /**
     * 修改昵称
     * @param username
     * @return
     */
    @Authorization
    @ApiOperation(value = UserConstant.UPDATE_USERNAME,httpMethod = "PUT")
    @PutMapping("/updateUsername")
    public Result<ObjectUtil> updateUsername(@RequestParam("username") String username,
                                             HttpSession session){
        Integer uid = (Integer) session.getAttribute("userId");
        User user = userService.getUserById(uid);
        user.setName(username);
        userService.update(user);
        return ResultBuilder.success();
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
        if (definition.length()>10){
            return ResultBuilder.fail(UserConstant.USER_DEFINITION_LENGTH);
        }
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        User user = userService.getUserById(uid);
        user.setDefinition(definition);
        userService.update(user);
        return ResultBuilder.success();
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
        return ResultBuilder.success();
    }
}

package com.test.redflower2.web.controller;

import com.test.redflower2.enums.UserInfoStateEnum;
import com.test.redflower2.exception.UserInfoException;
import com.test.redflower2.pojo.dto.ProfileDto;
import com.test.redflower2.pojo.dto.ResponseDto;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.UserService;
import com.test.redflower2.utils.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/user")
public class UserController {

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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseDto login1(@RequestParam(name = "code", defaultValue = "") String code,
                             HttpSession session) throws Exception {
        String openid = wechatUtil.getOpenId(code);
//        String openid = "2";
        if (code == null) {
            return ResponseDto.failed("log in failed, code is wrong");
        }

        User user = userService.login1(code);

        session.setAttribute("userId", user.getId());

        Map<String, Object> resData = new HashMap<>();
        resData.put("sessionId", session.getId());

        if (user.getState().equals(UserInfoStateEnum.INCOMPLETED.getValue())) {
            resData.put("completed", 1);
            return ResponseDto.succeed("not complete user info.", resData);
        } else {
            resData.put("completed", 0);
            return ResponseDto.succeed("log in successfully.", resData);
        }
    }



    /**
     * 返回登录后用户的信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    public ResponseDto getUserInfo(HttpSession session) throws UserInfoException {
        Integer userId =  (Integer) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        ProfileDto userInfo =  new ProfileDto(user.getName(),user.getDefinition(),user.getWxid(),user.getAvatarUrl());
        return ResponseDto.succeed("userInfo",userInfo);
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseDto logout(HttpSession session) {
        session.invalidate();
        return ResponseDto.succeed();
    }


    /**
     * 修改个人信息
     * @param name
     * @param definition
     * @param wxid
     * @param session
     * @return
     */

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseDto update(@RequestParam("name")String name,
                              @RequestParam("definition")String definition,
                              @RequestParam("wxid")String wxid,
                              HttpSession session){

        Integer userId = (Integer) session.getAttribute("userId");
        if(userId == null){
            return ResponseDto.failed("no login");
        }

        User user = userService.getUserById(userId);
        user.setName(name);
        user.setWxid(wxid);

        if (user.getDefinition().length()>10){
            return ResponseDto.failed("自定义信息长度不多于10个字符！");
        }
        user.setWxid(wxid);

        userService.update(user);
        return ResponseDto.succeed("modify successfully");

    }

    /**
     * 更新用户信息
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.PUT)
    public ResponseDto updateUserInfo(@RequestBody User user,HttpSession session){
        Integer uid = (Integer) session.getAttribute("userId");
        if (uid==null)
            return ResponseDto.failed("no uid");

        User suser=userService.getUserById(uid);
        user.setOpenid(suser.getOpenid());
        user.setId(suser.getId());
        user.setState(UserInfoStateEnum.COMPLETED.getValue());
        userService.update(user);

        return ResponseDto.succeed();

    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseDto test(){
            return ResponseDto.succeed("测试成功");
    }

}

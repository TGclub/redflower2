package com.test.redflower2.web.controller;

import com.test.redflower2.enums.UserInfoStateEnum;
import com.test.redflower2.exception.UserInfoException;
import com.test.redflower2.pojo.dto.ProfileDto;
import com.test.redflower2.pojo.dto.ResponseDto;
import com.test.redflower2.pojo.dto.Result;
import com.test.redflower2.pojo.dto.ResultBuilder;
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
    @PostMapping("/login")
    public ResponseDto login1(@RequestParam(name = "code", defaultValue = "") String code,
                             HttpSession session) throws Exception {
        
        if (code.equals("")||code==null) {
            return ResponseDto.failed("log in failed, code is wrong");
        }
        String openid = wechatUtil.getOpenId(code);
        if (openid == null) {
            return ResponseDto.failed("log in failed, openid is wrong");
        }
        User user = userService.login1(code);
        session.setAttribute("userId", user.getId());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("sessionId", session.getId());

        if (user.getState().equals(UserInfoStateEnum.INCOMPLETED.getValue())) {
            resultMap.put("incompleted", 0);
            return ResponseDto.succeed("not complete user info.", resultMap);
        } else {
            resultMap.put("completed", 0);
            return ResponseDto.succeed("log in successfully.", resultMap);
        }
    }



    /**
     * 返回登录后用户的信息
     * @param session
     * @return
     */

    @GetMapping("/userInfo")
    public Result<User> getUserInfo(HttpSession session) throws UserInfoException {
        Integer userId =  (Integer) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        return ResultBuilder.success(user);
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
     * 修改或完善个人信息
     * @param userParam 表单自动生成的user
     * @param session
     * @return
     */
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody User userParam, HttpSession session){

        Integer userId = (Integer) session.getAttribute("userId");

        User sUser = userService.getUserById(userId);
        userParam.setId(sUser.getId());
        userParam.setOpenid(sUser.getOpenid());
        userParam.setState(UserInfoStateEnum.COMPLETED.getValue());
        if (userParam.getDefinition().length()>10){
            return ResponseDto.failed("自定义信息长度不多于10个字符！");
        }
        userService.update(userParam);
        return ResponseDto.succeed();
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseDto test(){
            return ResponseDto.succeed("测试成功");
    }

}

package com.test.redflower2.service;

import com.test.redflower2.pojo.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 按用户id查询用户
     *
     * @param id
     * @return
     */
    User getUserById(Integer id);


    /**
     * 用户登录1
     * @param openid
     * @return
     */
    User isLoginSuccess1(String openid);

    /**
     * 用户登录0
     *
     * @param openId
     * @param session
     * @return
     */
    Map<Integer, String> isLoginSuccess(String openId, HttpSession session);


    /**
     * 更新用户名称
     *
     * @param uid
     * @return
     */
    String updateName(Integer uid, String username);


    /**
     * 更新用户个性签名
     *
     * @param definition
     * @param session
     * @return
     */
    String updateDefinition(String definition, HttpSession session);


    /**
     * 更新用户信息
     *
     * @param sUser
     * @return
     * @param　session
     */
    String updateUser(User sUser, HttpSession session);

    /**
     * 进入人脉圈中查看某一个用户的信息
     *
     * @param uid
     * @return
     */
    Map<Integer, User> getOneUserInfo(Integer uid);
}

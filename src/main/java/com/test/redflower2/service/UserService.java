package com.test.redflower2.service;

import com.test.redflower2.pojo.entity.User;
import javax.servlet.http.HttpSession;
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
     * 用户登录
     * @param openId
     * @param session
     * @return
     */
    Map<Integer, String> isLoginSuccess(String openId, HttpSession session);


    /**
     * 更新用户名称
     * @param uid
     * @return
     */
    String updateName(Integer uid, String username);


    /**
     * 更新用户个性签名
     * @param definition
     * @param session
     * @return
     */
    String updateDefinition(String definition, HttpSession session);


    /**
     * 更新用户信息
     * @param sUser
     * @param　session
     * @return
     */
    String updateUser(User sUser, HttpSession session);

}

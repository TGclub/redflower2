package com.test.redflower2.service;

import com.test.redflower2.pojo.entity.User;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 依据前台给的code，向微信请求openid，
     * 依据openid判断在数据库中是否存在，
     * 存在，则判断是否已完善显示
     * 已完善，则设置session，返回成功
     * 未完善，设置session，返回需完善信息
     * 不存在，则创建用户，返回需完善信息
     *
     * @param openid 前台给的code
     * @return User Entity
     */


    /**
     * 对用户信息进行更新
     * 1. 完善信息
     * 2. 修改信息
     * ...
     *
     * @param user 修改后的user
     */
    void update(User user);

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
     * 显示人脉网随机某一个用户的个人信息，并且显示与主用户之间的亲密度
     * @param user
     * @param session
     * @return
     */
    Map<String, List<User>> getNetworkUserInfo(User user, HttpSession session);

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
}

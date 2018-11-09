package com.test.redflower2.service.impl;

import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.dao.UserDao;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.UserService;
import com.test.redflower2.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Component
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;




    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    /**
     * 通过id查询user
     *
     * @param id
     * @return
     */
    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }


    /**
     * 更新用户信息
     *
     * @param user    表单用户
     * @param session
     * @return
     */
    @Override
    public String updateUser(User user, HttpSession session) {
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        User sUser = userDao.getUserById(uid);
        if (ObjectUtil.isEmpty(sUser)) {
            return UserConstant.FAIL_MSG;
        } else {
            user.setId(uid);
            user.setOpenid(sUser.getOpenid());
            userDao.save(user);
            return UserConstant.SUCCESS_MSG;
        }
    }


    /**
     * 用户登录
     */

    public User isLoginSuccess1(String openid){
        User user = userDao.getUserByOpenid(openid);
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user= userDao.save(user);
        }
        return user;
    }

    /**
     * 用户登录
     *
     * @param openId
     * @return
     */
    @Override
    public Map<Integer, String> isLoginSuccess(String openId, HttpSession session) {
        Map<Integer, String> datas = new HashMap<>();
        if (ObjectUtil.isStringEmpty(openId)) {
            int status;
            status = UserConstant.FAILED_CODE;
            datas.put(status, UserConstant.OPENID_NULL);
        }

        User user = userDao.getUserByOpenid(openId);
        //user为空，则新建一个user并保存
        if (ObjectUtil.isEmpty(user)) {
            int status;
            user = new User();
            user.setOpenid(openId);
            userDao.save(user);
            status = UserConstant.SUCCESS_CODE;
            session.setAttribute(UserConstant.USER_ID, user.getId());
            datas.put(status, UserConstant.SUCCESS_MSG);
        } else {
            session.setAttribute(UserConstant.USER_ID, user.getId());//使用户登录
            datas.put(UserConstant.SUCCESS_CODE,UserConstant.SUCCESS_MSG);
        }
        return datas;
    }

    /**
     * 进入人脉圈后查看某一个用户的信息
     * @param uid
     * @return
     */
    @Override
    public Map<Integer,User> getOneUserInfo(Integer uid) {
        Map<Integer,User> datas =new HashMap<>();
        User user = userDao.getUserById(uid);
        if (ObjectUtil.isEmpty(user)){
            datas.put(UserConstant.FAILED_CODE,user);
        }else {
            datas.put(UserConstant.SUCCESS_CODE,user);
        }
        return datas;
    }

    /**
     * 更新用户个性签名
     *
     * @param definition
     * @param session
     * @return
     */
    @Override
    public String updateDefinition(String definition, HttpSession session) {
        if (definition.length() > 30) {
            return UserConstant.USER_DEFINITION_LENGTH;
        } else {
            Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
            User user = userDao.getUserById(uid);
            String newDefinition = ObjectUtil.getStringFilter(definition);
            user.setDefinition(newDefinition);
            userDao.save(user);
            return UserConstant.SUCCESS_MSG;
        }
    }


    /**
     * 更新用户名称
     *
     * @param uid
     * @param username
     * @return
     */
    @Override
    public String updateName(Integer uid, String username) {
        User user = userDao.getUserById(uid);
        String username1 = user.getName();
        if (username1.equals(username)) {
            return UserConstant.FAIL_MSG;
        } else {
            user.setName(username);
            userDao.save(user);
            return UserConstant.SUCCESS_MSG;
        }
    }
}

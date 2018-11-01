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
    private  UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao){this.userDao = userDao;}




    /**
     * 更新完善个人信息
     * @param user 修改后的user
     */
    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    /**
     * 用户登录
     * @param openId
     * @return
     */
    @Override
    public Map<String, Integer> isLoginSuccess(String openId, HttpSession session) {
        Map<String,Integer> datas = new HashMap<>();
        if (ObjectUtil.isEmpty(openId)){
            int status;
            status=UserConstant.FAILED_CODE;
            datas.put(UserConstant.FAIL_MSG,status);
        }
        User user = userDao.getUserByOpenid(openId);
        //user为空，则新建一个user并保存
        if (ObjectUtil.isEmpty(user)){
            int status;
            user = new User();
            user.setOpenid(openId);
            user.setState(UserConstant.USER_INFO_INCOMPLETED);
            userDao.save(user);
            status=UserConstant.SUCCESS_CODE;
            session.setAttribute(UserConstant.USER_ID,user.getId());
            datas.put(UserConstant.SUCCESS_MSG,status);
        }
        return datas;
    }
}

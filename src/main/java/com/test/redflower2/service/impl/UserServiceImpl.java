package com.test.redflower2.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.test.redflower2.dao.UserDao;
import com.test.redflower2.enums.UserInfoStateEnum;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.UserService;
import com.test.redflower2.utils.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao){this.userDao = userDao;}

    @Override
    public User login1(String openId) {
        User user = userDao.getUserByOpenid(openId);
        if (user == null) {
            user = new User();
            user.setOpenid(openId);
            user.setState(UserInfoStateEnum.INCOMPLETED.getValue());
            user= userDao.save(user);
        }
        return user;
    }

    @Override
    public User login(String openid, String encryptedData, String sessionkey, String iv) {
        try {

            User user = userDao.getUserByOpenid(openid);
            //用户信息解密
            String result = AesUtil.decrypt(encryptedData, sessionkey, iv);
            if (null != result && result.length() > 0) {

                JSONObject userInfoJSON = JSONObject.parseObject(result);
                if (user == null) {
                    user = new User();
                    user.setGender(Integer.parseInt(userInfoJSON.getString("gender")));
                    user.setOpenid(openid);
                    user.setSessionKey(sessionkey);
                    user.setAvatarUrl(userInfoJSON.getString("avatarUrl"));
                    user = userDao.save(user);
                    return user;
                }

                user.setSessionKey(sessionkey);
                user.setAvatarUrl(userInfoJSON.getString("avatarUrl"));
                user = userDao.save(user);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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

}

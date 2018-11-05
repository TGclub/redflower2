package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>Created on 18-11-5</p>
 *
 * @author:StormWangxhu
 * @description: <p>描述</p>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    private static final Logger LOGGER= LoggerFactory.getLogger(UserDaoTest.class);

    @Test
    public void testGetUserById(){
        User result = userDao.getUserById(1);
        LOGGER.info("getUserById()测试结果:{}",result);
    }

    @Test
    public void testGetUserByOpenid(){
        String openid= "openid_test1";
        User result = userDao.getUserByOpenid(openid);
        LOGGER.info("getUserByOpenid()测试结果:{}",result);
    }

}

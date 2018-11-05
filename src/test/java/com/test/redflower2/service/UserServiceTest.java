package com.test.redflower2.service;

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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

    /**
     * id查询
     */
    @Test
    public void testGetUserById(){
        Integer id =1;
        User result =userService.getUserById(id);
        LOGGER.info("测试结果为:{}",result);
    }

//    /**
//     * 登录
//     */
//    @Test
//    public void testIsLoginSuccess(){
//        String openId ="oOSOp5aiX5dI1WB6vnqf2Kebuty4";
//        HttpServletRequest request ;
//        HttpSession session = request.getSession();
//        Map<Integer,String> result = userService.isLoginSuccess(openId,session);
//        LOGGER.info("测试结果为{}",result);
//
//    }

//    @Test
//    public void testUpdateName(){
//        Integer uid = 3;
//        String newUsername ="stormwangxhu";
//        String result = userService.updateName(uid,newUsername);
//        LOGGER.info("测试结果:{}",result);
//    }

}

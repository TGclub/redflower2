package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.UserNetwork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <p>Created on 18-11-5</p>
 *
 * @author:StormWangxhu
 * @description: <p>描述</p>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserNetworkDaoTest {

    @Autowired
    private UserNetworkDao userNetworkDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserNetworkDaoTest.class);

    @Test
    public void testGetUserNetworkByUid(){
        Integer uid = 1;
        List<UserNetwork> result = userNetworkDao.getUserNetworkByFid(uid);
        LOGGER.info("getUserNetworkByUid()测试结果{}",result);
    }

    @Test
    public void testGetUserNetworkByFidAndNid(){
        Integer uid =1;
        Integer fid=1;
        UserNetwork result = userNetworkDao.getUserNetworkByFidAndNid(uid,fid);
        LOGGER.info("测试结果为{}",result);
    }

    @Test
    public void testGetUserNetworkById(){
        Integer id =1;
        UserNetwork result = userNetworkDao.getUserNetworkById(id);
        LOGGER.info("测试结果为{}",result);
    }


    @Test
    public void testGetUserNetworkByNid(){
        Integer nid =1;
        List<UserNetwork> result = userNetworkDao.getUserNetworksByNid(nid);
        LOGGER.info("测试结果为{}",result);
    }

    @Test
    public void testFindAllByFid(){
        Integer uid =1;
        List<UserNetwork> result = userNetworkDao.findAllByFid(uid);
        LOGGER.info("测试结果为{}",result);
    }


}

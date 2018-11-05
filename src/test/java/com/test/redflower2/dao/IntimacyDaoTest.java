package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.Intimacy;
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
public class IntimacyDaoTest {

    @Autowired
    private IntimacyDao intimacyDao;

    private static final Logger LOGGER= LoggerFactory.getLogger(IntimacyDao.class);

    @Test
    public void testGetIntimacyByUserValueAndFormValue(){
        Integer formValue = 10;
        Integer userValue =0;
        Intimacy result = intimacyDao.getIntimacyByUserValueAndFormValue(userValue,formValue);
        LOGGER.info("测试结果:{}",result);
    }


}

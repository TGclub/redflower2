package com.test.redflower2.service.impl;

import com.test.redflower2.dao.IntimacyDao;
import com.test.redflower2.pojo.entity.Intimacy;
import com.test.redflower2.service.IntimacyService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class IntimacyServiceImpl implements IntimacyService {

    private IntimacyDao intimacyDao;

    public IntimacyServiceImpl(IntimacyDao intimacyDao) {
        this.intimacyDao = intimacyDao;
    }

    @Override
    public Intimacy getIntimacyByUid1AndUid2(Integer uid1, Integer uid2) {
        return intimacyDao.getIntimacyByUid1AndUid2(uid1, uid2);
    }
}

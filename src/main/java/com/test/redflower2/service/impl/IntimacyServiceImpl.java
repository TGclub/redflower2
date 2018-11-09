package com.test.redflower2.service.impl;

import com.test.redflower2.service.IntimacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class IntimacyServiceImpl implements IntimacyService {

    private IntimacyDao intimacyDao;

    @Autowired
    public IntimacyServiceImpl(IntimacyDao intimacyDao) {
        this.intimacyDao = intimacyDao;
    }

    @Override
    public Intimacy getIntimacyByUserValueAndFormValue(Integer uid1, Integer uid2) {
        return intimacyDao.getIntimacyByUserValueAndFormValue(uid1, uid2);
    }
}

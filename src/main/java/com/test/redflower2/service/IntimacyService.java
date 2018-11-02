package com.test.redflower2.service;


import com.test.redflower2.pojo.entity.Intimacy;

public interface IntimacyService {

    Intimacy getIntimacyByUserValueAndFormValue(Integer uid1, Integer uid2);
}

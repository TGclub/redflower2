package com.test.redflower2.dao;


import com.test.redflower2.pojo.entity.Intimacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntimacyDao extends JpaRepository<Intimacy,Integer> {
    Intimacy getIntimacyByUid1AndUid2(Integer uid1, Integer uid2);
}

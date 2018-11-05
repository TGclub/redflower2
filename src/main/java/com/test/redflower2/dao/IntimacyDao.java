package com.test.redflower2.dao;


import com.test.redflower2.pojo.entity.Intimacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntimacyDao extends JpaRepository<Intimacy,Integer> {

    /**
     * 根据中心用户和表单用户值查询亲密度
     * @param userValue
     * @param formValue
     * @return
     */

    Intimacy getIntimacyByUserValueAndFormValue(Integer userValue, Integer formValue);
}

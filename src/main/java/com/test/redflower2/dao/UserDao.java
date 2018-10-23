package com.test.redflower2.dao;


import com.test.redflower2.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User getUserByOpenid(String openid);

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    User getUserById(Integer id);
}

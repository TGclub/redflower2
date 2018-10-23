package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.UserNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNetworkDao extends JpaRepository<UserNetwork,Integer> {

    UserNetwork getUserNetworkByUidAndNid(Integer uid, Integer nid);

    List<UserNetwork> getUserNetworksByUid(Integer uid);

    List<UserNetwork> getUserNetworksByNid(Integer nid);
}

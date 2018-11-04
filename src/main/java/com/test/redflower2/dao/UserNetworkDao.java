package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.UserNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNetworkDao extends JpaRepository<UserNetwork,Integer> {

    UserNetwork getUserNetworkByUidAndNid(Integer uid, Integer nid);

    /**
     * 通过uid查询network,我的人脉圈
     * @param uid
     * @return
     */
    List<UserNetwork> getUserNetworksByUid(Integer uid);

    /**
     * 通过nid查询
     * @param nid
     * @return
     */

    List<UserNetwork> getUserNetworksByNid(Integer nid);


    /**
     * 通过用户的id查询出用户的usernetwork
     * @param uid
     * @return
     */
    List<UserNetwork> getUserNetworkByUid(Integer uid);
}

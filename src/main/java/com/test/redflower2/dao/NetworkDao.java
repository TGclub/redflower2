
package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkDao extends JpaRepository<Network,Integer> {


    /**
     * 通过用户id查找人脉网
     * @param uid
     * @return
     */
    Network getNetworkByUid(Integer uid);

    /**
     * 通过nid 查询network
     * @param nid
     * @return
     */
    Network getNetworkById(Integer nid);

}

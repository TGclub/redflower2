
package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkDao extends JpaRepository<Network, Integer> {

    /**
     * 通过用户id查找人脉网
     *
     * @param uid
     * @return
     */

    Network getNetworkByUid(Integer uid);

    /**
     * 通过id查询network
     *
     * @param nid
     * @return
     */

    Network getNetworkById(Integer nid);

    /**
     * 根据用户uid和圈子id查询出当前用户所属于的人脉圈
     *
     * @param uid
     * @param nid
     * @return
     */
    Network getNetworkByUidAndId(Integer uid, Integer nid);

    /**
     * 根据uid查询出所有该群主的群
     *
     * @param uid
     * @return
     */
    List<Network> findAllByUid(Integer uid);
}

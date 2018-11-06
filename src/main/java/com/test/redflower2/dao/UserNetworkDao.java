package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.UserNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNetworkDao extends JpaRepository<UserNetwork, Integer> {

    /**
     * test 测试
     *
     * @param id
     * @return
     */
    UserNetwork getUserNetworkById(Integer id);

    /**
     * 通过uid和nid确定用户和群对应关系
     *
     * @param uid
     * @param nid
     * @return
     */
    UserNetwork getUserNetworkByFidAndNid(Integer uid, Integer nid);

    /**
     * 通过nid查询
     *
     * @param nid
     * @return
     */

    List<UserNetwork> getUserNetworksByNid(Integer nid);

    /**
     * 通过uid查询出所有映射关系
     *
     * @param fid
     * @return
     */
    List<UserNetwork> findAllByFid(Integer fid);


    /**
     * 通过用户的id查询出用户的usernetwork
     *
     * @param fid
     * @return
     */

    List<UserNetwork> getUserNetworkByFid(Integer fid);

    /**
     * 根据uid和nid查询出人数
     *
     * @param fid
     * @param nid
     * @return
     */
    List<UserNetwork> findAllByFidAndNid(Integer fid, Integer nid);

    /**
     * 查询出所有得关系
     *
     * @param nid
     * @return
     */
    List<UserNetwork> findAllByNid(Integer nid);
}


package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkDao extends JpaRepository<Network,Integer> {

    Network getNetworkById(Integer id);

//    List<Network> getNetworkByNid(Integer nid);
}

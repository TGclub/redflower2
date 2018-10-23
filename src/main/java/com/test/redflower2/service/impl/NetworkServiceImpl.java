package com.test.redflower2.service.impl;

import com.test.redflower2.dao.NetworkDao;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class NetworkServiceImpl implements NetworkService {

    private NetworkDao networkDao;

    @Autowired
    public NetworkServiceImpl(NetworkDao networkDao) {
        this.networkDao = networkDao;
    }

    @Override
    public void insert(Network network) {
        networkDao.save(network);
    }

//    @Override
//    public List<Network> getNetworkByNid(Integer integer) {
//        return networkDao.getNetworkByNid(integer);
//    }
}

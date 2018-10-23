package com.test.redflower2.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.test.redflower2.dao.NetworkDao;
import com.test.redflower2.dao.UserNetworkDao;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.UserNetwork;
import com.test.redflower2.service.UserNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class UserNetworkServiceImpl implements UserNetworkService {

    private UserNetworkDao userNetworkDao;
    private NetworkDao networkDao;

    @Autowired
    public UserNetworkServiceImpl(UserNetworkDao userNetworkDao, NetworkDao networkDao) {
        this.userNetworkDao = userNetworkDao;
        this.networkDao = networkDao;
    }

    @Override
    public void insert(UserNetwork userNetwork) {
        userNetworkDao.save(userNetwork);
    }

    @Override
    public UserNetwork getUserNetworkByUidAndNid(Integer uid, Integer nid) {
        return userNetworkDao.getUserNetworkByUidAndNid(uid, nid);
    }

    @Override
    public List<UserNetwork> getUserNetworksByUid(Integer uid) {
        return userNetworkDao.getUserNetworksByUid(uid);
    }

    @Override
    public List<UserNetwork> getUserNetworksByNid(Integer nid) {
        return userNetworkDao.getUserNetworksByNid(nid);
    }

    @Override
    public JSONObject myNetworks(Integer uid) {
        List<UserNetwork> userNetworks = userNetworkDao.getUserNetworksByUid(uid);
        JSONObject response = new JSONObject();

        for (int i = 0; i < userNetworks.size(); ++i) {

            Integer nid = userNetworks.get(i).getNid();

            //获得id为nid的network，得到该network的name
            Network network = networkDao.getNetworkById(nid);
            JSONObject json = new JSONObject();

            //获得关系表中nid为nid的全部映射，使用size()方法得到人数
            List<UserNetwork> userNetworks1 = userNetworkDao.getUserNetworksByNid(nid);

            json.put(network.getName(), userNetworks1.size());
            response.put(network.getId().toString(), json);
        }

        return response;
    }
}

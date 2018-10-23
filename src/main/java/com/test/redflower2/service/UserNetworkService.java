package com.test.redflower2.service;

import com.alibaba.fastjson.JSONObject;
import com.test.redflower2.pojo.entity.UserNetwork;

import java.util.List;

public interface UserNetworkService {

    void insert(UserNetwork userNetwork);

    UserNetwork getUserNetworkByUidAndNid(Integer uid, Integer nid);

    List<UserNetwork> getUserNetworksByUid(Integer uid);

    List<UserNetwork> getUserNetworksByNid(Integer nid);

    JSONObject myNetworks(Integer uid);
}

package com.test.redflower2.service;

import com.alibaba.fastjson.JSONObject;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.pojo.entity.UserNetwork;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserNetworkService {

    void insert(UserNetwork userNetwork);

    UserNetwork getUserNetworkByUidAndNid(Integer uid, Integer nid);

    List<UserNetwork> getUserNetworksByNid(Integer nid);

    JSONObject myNetworks(Integer uid);

    /**
     * 邀请更多用户加入人脉网
     * @param user
     * @param session
     * @return
     */
    Map<String, Integer> inviteMoreUser(User user, HttpSession session);

    /**
     * 查看我的人脉圈
     * @param uid
     * @return
     */
    Map<String, List<Network>> getNetworksByUid(Integer uid);

}

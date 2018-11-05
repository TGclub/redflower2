package com.test.redflower2.service;

import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.User;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserNetworkService {

    /**
     * 邀请更多用户加入人脉网
     * @param user
     * @param session
     * @return
     */
    Map<Integer, String> inviteMoreUser(User user, Network network,HttpSession session);

    /**
     * 查看我的人脉圈
     * @param uid
     * @return
     */
    Map<Integer, List<Network>> getNetworksByUid(Integer uid);

    /**
     * 查看人脉网用户信息
     * @param userForm
     * @param session
     * @return
     */
    Map<Integer, Object> getNetworkUserInfo(User userForm, HttpSession session);

    /**
     * 返回中心用户周围用户的所有用户信息
     * @param user
     * @param session
     * @return
     */
    List<User> getNetworkUserInfo1(User user,HttpSession session);

    /**
     * 查看我的人脉圈以及对应的人数
     * @param uid
     * @return
     */
    Map<String, Integer> getMyNetworkUserSum(Integer uid);
}

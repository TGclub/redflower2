package com.test.redflower2.service;



import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface NetworkService {

    /**
     * 创建新的人脉圈
     * @param networkName
     * @param networkUrl
     * @param session
     * @return
     */
    Map<Integer, String> createNetwork1(String networkName,String networkUrl, HttpSession session);


    /**
     * 查看我的人脉圈
     * @param uid
     * @return
     */
    Map<Integer, List<Network>> getNetworksByUid(Integer uid);

    /**
     * 查看我的各个人脉圈总人数
     * @param uid
     * @return
     */
    Map<String, Integer> getMyNetworkUserSum(Integer uid);

    /**
     * 查询出进入我的某个人脉圈所有的用户
     * @param uid
     * @return
     */
    Map<Integer, List<User>> getMyAllUsers(Integer nid ,Integer uid);

    /**
     * 人脉网界面随机点击某个用户,再列出该用户的全部好友
     * @param user
     * @param session
     * @return
     */
    Map<Integer,List<User>> getNetworksUserInfo(User user, HttpSession session);


    /**
     * 邀请更多人加入人脉
     * @param user
     * @param network
     * @param session
     * @return
     */
    Map<Integer, String> inviteMoreUser(User user, Network network, HttpSession session);
}

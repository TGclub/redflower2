package com.test.redflower2.service;

import com.test.redflower2.pojo.entity.Network;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface NetworkService {

    void insert(Network network);


    /**
     * 创建新的人脉圈
     * @param networkName
     * @param session
     * @return
     */
    Map<String, Integer> createNetwork(String networkName, HttpSession session);
}

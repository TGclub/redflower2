package com.test.redflower2.service;



import javax.servlet.http.HttpSession;
import java.util.Map;

public interface NetworkService {

    /**
     * 创建新的人脉圈
     * @param networkName
     * @param session
     * @return
     */
    Map<Integer, String> createNetwork(String networkName, HttpSession session);

    /**
     * 创建新的人脉圈
     * @param networkName
     * @param networkUrl
     * @param session
     * @return
     */
    Map<Integer, String> createNetwork1(String networkName,String networkUrl, HttpSession session);

}

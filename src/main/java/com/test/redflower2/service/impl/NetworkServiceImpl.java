package com.test.redflower2.service.impl;

import com.test.redflower2.constant.NetworkConstant;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.dao.NetworkDao;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.service.NetworkService;
import com.test.redflower2.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Component
public class NetworkServiceImpl implements NetworkService {

    private NetworkDao networkDao;

    @Autowired
    public NetworkServiceImpl(NetworkDao networkDao) {
        this.networkDao = networkDao;
    }

    /**
     * 创建新的人脉圈
     *
     * @param networkName
     * @param session
     * @return
     */
    @Override
    public Map<Integer, String> createNetwork(String networkName, HttpSession session) {
        Map<Integer, String> datas = new HashMap<>();
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        Network network = new Network();
        //若人脉圈名称为空，则创建失败
        if (ObjectUtil.isStringEmpty(networkName)) {
            int status;
            status = NetworkConstant.FAIL_CODE;
            datas.put(status, NetworkConstant.NULL);
            return datas;
        } else {//否则创建成功
            //人脉圈默认人数为1个
            int count = 1;
            int status;
            network.setUid(uid);
            network.setNetworkName(networkName);
            network.setCount(count);
            networkDao.save(network);
            status = NetworkConstant.SUCCESS_CODE;
            datas.put(status, NetworkConstant.SUCCESS);
            return datas;
        }
    }

    /**
     *      * 创建新人脉圈
     * @param networkName
     * @param networkUrl
     * @param session
     * @return
     */
    public Map<Integer, String> createNetwork1(String networkName,String networkUrl, HttpSession session) {
        Map<Integer, String> datas = new HashMap<>();
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
//        String networkName = network.getNetworkName();
        Network network1 = new Network();
        //若人脉圈名称为空，则创建失败
        if (ObjectUtil.isStringEmpty(networkName)) {
            int status;
            status = NetworkConstant.FAIL_CODE;
            datas.put(status, NetworkConstant.NULL);
            return datas;
        } else {
            //人脉圈默认人数为1个
            int count = 1;
            int status;
            network1.setUid(uid);
            network1.setNetworkName(networkName);
//            network1.setNetworkUrl(network.getNetworkUrl());
            network1.setNetworkUrl(networkUrl);
            network1.setCount(count);
            networkDao.save(network1);
            status = NetworkConstant.SUCCESS_CODE;
            datas.put(status, NetworkConstant.SUCCESS);
            return datas;
        }
    }
}

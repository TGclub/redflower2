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

    @Override
    public void insert(Network network) {
        networkDao.save(network);
    }

    /**
     * 创建新的人脉圈
     * @param networkName
     * @param session
     * @return
     */
    @Override
    public Map<Integer, String> createNetwork(String networkName, HttpSession session) {
        Map<Integer,String> datas = new HashMap<>();
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        Network network = new Network();
        //若人脉圈名称为空，则创建失败
        if (ObjectUtil.isEmpty(networkName)){
            int status ;
            status = NetworkConstant.FAIL_CODE;
            datas.put(status,NetworkConstant.FAIL);
            return datas;
        }else {//否则创建成功
            int status;
            network.setUid(uid);
            network.setNetworkName(networkName);
            networkDao.save(network);
            status=NetworkConstant.SUCCESS_CODE;
            datas.put(status,NetworkConstant.SUCCESS);
            return datas;
        }
    }
}

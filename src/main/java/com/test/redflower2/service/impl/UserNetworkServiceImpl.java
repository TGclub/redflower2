package com.test.redflower2.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.test.redflower2.constant.NetworkConstant;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.dao.NetworkDao;
import com.test.redflower2.dao.UserNetworkDao;
import com.test.redflower2.pojo.dto.ResponseDto;
import com.test.redflower2.pojo.dto.ResultBuilder;
import com.test.redflower2.pojo.entity.Intimacy;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.pojo.entity.UserNetwork;
import com.test.redflower2.service.UserNetworkService;
import com.test.redflower2.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Network network = networkDao.getNetworkByUid(nid);
            JSONObject json = new JSONObject();

            //获得关系表中nid为nid的全部映射，使用size()方法得到人数
            List<UserNetwork> userNetworks1 = userNetworkDao.getUserNetworksByNid(nid);

            json.put(network.getNetworkName(), userNetworks1.size());
            response.put(network.getId().toString(), json);
        }

        return response;
    }

    /**
     * 邀请更多人加入人脉网
     * @param user
     * @param session
     * @return
     */
    @Override
    public Map<String, Integer> inviteMoreUser(User user, HttpSession session) {
        Map<String, Integer> datas = new HashMap<>();
        Integer uid =(Integer) session.getAttribute(UserConstant.USER_ID);//得到当前用户B的id
        Network network = networkDao.getNetworkByUid(uid);//查询出当前用户人B人脉圈nid
        Integer nid = network.getId();//B人脉圈的nid
        UserNetwork userNetwork =new UserNetwork();
        Integer sUserId = user.getId();//要被邀请进来的人A id
        if (ObjectUtil.isEmpty(sUserId)){//不存在该用户
            int status =NetworkConstant.FAIL_CODE;
            datas.put(NetworkConstant.NOT_EXIST,status);
            return datas;
        }else if (userNetworkDao.getUserNetworkByUidAndNid(sUserId, nid) != null) {
            int status=NetworkConstant.FAIL_CODE;
            datas.put(NetworkConstant.ALREADY_EXIST,status);
            return datas;
        } else {
            int status;
            userNetwork.setNid(nid);//被邀请人B人脉圈的nid,他们应该对应B的nid
            userNetwork.setUid(sUserId);
            userNetworkDao.save(userNetwork);
            status=NetworkConstant.FAIL_CODE;
            datas.put(NetworkConstant.SUCCESS,status);
            return datas;
        }
    }
}

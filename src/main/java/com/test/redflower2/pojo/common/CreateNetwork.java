package com.test.redflower2.pojo.common;

/**
 * <p>Created on 18-11-6</p>
 *
 * @author:StormWangxhu
 * @description: <p>描述</p>
 */

import com.test.redflower2.constant.NetworkConstant;
import com.test.redflower2.dao.NetworkDao;
import com.test.redflower2.dao.UserNetworkDao;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.UserNetwork;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 创建公共群
 */
public class CreateNetwork {

    private NetworkDao networkDao;

    private UserNetworkDao userNetworkDao;

    @Autowired
    public CreateNetwork(NetworkDao networkDao,UserNetworkDao userNetworkDao){
        this.networkDao=networkDao;
        this.userNetworkDao=userNetworkDao;
    }

    public CreateNetwork(){

    }

    /**
     * 创建三个默认的群兵保存
     * @param uid
     * @return
     */
    public void createNetworks(Integer uid) {
        Network friendCircle = new Network();//朋友圈
        Network spreadQuesCircle = new Network(); //传播问题的群
        Network answerQuesCirclr = new Network();//回答问题的

        //朋友圈
        friendCircle.setUid(uid);
        friendCircle.setNetworkName(NetworkConstant.FRIENDCIRCLE);
        UserNetwork frienfUserNetwork = createRelationBetwenNetworkAndFriends(friendCircle.getId(),uid);
        networkDao.save(friendCircle);//保存
        userNetworkDao.save(frienfUserNetwork);

        //帮传播问题的人
        spreadQuesCircle.setUid(uid);
        spreadQuesCircle.setNetworkName(NetworkConstant.SPREADCIRCLE);
        UserNetwork spreadUsernetwork =createRelationBetwenNetworkAndFriends(spreadQuesCircle.getId(),uid);
        networkDao.save(spreadQuesCircle);
        userNetworkDao.save(spreadUsernetwork);

        //帮我回答问题的人
        answerQuesCirclr.setUid(uid);
        answerQuesCirclr.setNetworkName(NetworkConstant.ANSWERCIRCLR);
        UserNetwork answerUsernetwork = createRelationBetwenNetworkAndFriends(answerQuesCirclr.getId(),uid);
        networkDao.save(answerQuesCirclr);
        userNetworkDao.save(answerUsernetwork);
    }



    /**
     * 维护关系并保存
     * @param fid
     * @return
     */
    public UserNetwork createRelationBetwenNetworkAndFriends(Integer nid,Integer fid) {
        UserNetwork userNetwork = new UserNetwork();
        userNetwork.setNid(nid);
        userNetwork.setFid(fid);
        userNetworkDao.save(userNetwork);
        return userNetwork;
    }

}

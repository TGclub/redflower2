package com.test.redflower2.service.impl;

import com.test.redflower2.constant.NetworkConstant;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.dao.NetworkDao;
import com.test.redflower2.dao.UserDao;
import com.test.redflower2.dao.UserNetworkDao;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.pojo.entity.UserNetwork;
import com.test.redflower2.service.NetworkService;
import com.test.redflower2.utils.ObjectUtil;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Component
public class NetworkServiceImpl implements NetworkService {

    private NetworkDao networkDao;

    private UserNetworkDao userNetworkDao;

    private UserDao userDao;

    @Autowired
    public NetworkServiceImpl(NetworkDao networkDao, UserNetworkDao userNetworkDao, UserDao userDao) {
        this.networkDao = networkDao;
        this.userNetworkDao = userNetworkDao;
        this.userDao = userDao;
    }


    /**
     * 创建新人脉圈
     *
     * @param networkName
     * @param networkUrl
     * @param session     map  key: 状态码,value:人脉圈id
     * @return
     */
    public Map<Integer, Object> createNetwork1(String networkName, String networkUrl,
                                               HttpSession session) {
        Map<Integer, Object> datas = new HashMap<>();
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        Network network1 = new Network();
        //若人脉圈名称为空，则创建失败
        networkName.
        if (ObjectUtil.isStringEmpty(networkName)) {
            datas.put(NetworkConstant.FAIL_CODE, NetworkConstant.NULL);
            return datas;
            //下面三句判断名称是否与默认人脉圈名称相同
        } else if (networkName.equals(NetworkConstant.FRIENDCIRCLE)) {
            datas.put(NetworkConstant.FAIL_CODE, NetworkConstant.NAME_EXIST);
            return datas;
        } else if (networkName.equals(NetworkConstant.SPREADCIRCLE)) {
            datas.put(NetworkConstant.FAIL_CODE, NetworkConstant.NAME_EXIST);
            return datas;
        } else if (networkName.equals(NetworkConstant.ANSWERCIRCLR)) {
            datas.put(NetworkConstant.FAIL_CODE, NetworkConstant.NAME_EXIST);
            return datas;
        } else {
            network1.setUid(uid);
            network1.setNetworkName(networkName);
            network1.setNetworkUrl(networkUrl);
            networkDao.save(network1);
            //维护关系并保存
            createRelationBetwenNetworkAndFriends(network1.getId(), uid);
            datas.put(NetworkConstant.SUCCESS_CODE, network1.getId());
            return datas;
        }
    }

    /**
     * 为每一个新用户创建三个默认的群
     *
     * @param uid
     * @return
     */
    @Override
    public List<Integer> createThreeCircle(Integer uid) {
        //保存三个默认人脉圈的id
        List<Integer> idList = new ArrayList<>();
        Network friendCircle = new Network();//朋友圈
        Network spreadQuesCircle = new Network(); //传播问题的群
        Network answerQuesCirclr = new Network();//回答问题的

        //朋友圈
        friendCircle.setUid(uid);
        friendCircle.setNetworkName(NetworkConstant.FRIENDCIRCLE);
        friendCircle.setNetworkUrl(NetworkConstant.FRIEND_CIRCLE_URL);
        networkDao.save(friendCircle);//保存
        UserNetwork frienfUserNetwork = createRelationBetwenNetworkAndFriends(friendCircle.getId(), uid);
        userNetworkDao.save(frienfUserNetwork);
        idList.add(friendCircle.getId());

        //帮传播问题的人
        spreadQuesCircle.setUid(uid);
        spreadQuesCircle.setNetworkName(NetworkConstant.SPREADCIRCLE);
        spreadQuesCircle.setNetworkUrl(NetworkConstant.SPREAD_CIRCLE_URL);
        networkDao.save(spreadQuesCircle);
        UserNetwork spreadUsernetwork = createRelationBetwenNetworkAndFriends(spreadQuesCircle.getId(), uid);
        userNetworkDao.save(spreadUsernetwork);
        idList.add(spreadQuesCircle.getId());

        //帮我回答问题的人
        answerQuesCirclr.setUid(uid);
        answerQuesCirclr.setNetworkName(NetworkConstant.ANSWERCIRCLR);
        answerQuesCirclr.setNetworkUrl(NetworkConstant.ANSWER_CIRCLE_URL);
        networkDao.save(answerQuesCirclr);
        UserNetwork answerUsernetwork = createRelationBetwenNetworkAndFriends(answerQuesCirclr.getId(), uid);
        userNetworkDao.save(answerUsernetwork);
        idList.add(answerQuesCirclr.getId());
        return idList;
    }


    /**
     * 维护人和群之间的关系并保存
     *
     * @param uid
     * @return
     */
    public UserNetwork createRelationBetwenNetworkAndFriends(Integer nid, Integer uid) {
        UserNetwork userNetwork = new UserNetwork();
        userNetwork.setNid(nid);
        userNetwork.setUid(uid);
        userNetworkDao.save(userNetwork);
        return userNetwork;
    }

    /**
     * done:查看我的人脉圈列表
     * 根据群主id查询出所有networK    并返回每个群的人数
     *
     * @param uid
     * @return
     */
    @Override
    public List<Object> getNetworksAndCountByUid(Integer uid) {

        List<Object> objectList = new ArrayList<>();
        /**
         * 1.查出该uid所有的群id
         */
        List<Network> networkList = networkDao.findAllByUid(uid);
        if (networkList.size() == 0) {//大小为0,说明改用户还没有群
            objectList.add(null);
            return objectList;
        } else {//有群
            /**
             * 2.计算每个群的人数
             * key:群  ,value:人数
             */
            for (int i = 0; i < networkList.size(); ++i) {
                //根据每个群的id在user_network去查询该群的人数总和
                Integer nid = networkList.get(i).getId();
                Network network = networkList.get(i);
                List<UserNetwork> userNetworkList = userNetworkDao.findAllByNid(nid);
                if (userNetworkList.size() == 1) {//大小为1,说明该群里只有群主一个人
                    network.setCount(1);
                    objectList.add(network);
                } else {//有群友
                    //计算networklist大小,即为群人数
                    int sum = 1;
                    for (int j = 0; j < userNetworkList.size(); ++j) {
                        sum += sum;
                    }
                    network.setCount(sum);
                    objectList.add(network);
                }
            }
            return objectList;
        }
    }


    /**
     * 查询出某一个人脉圈中所有的用户
     *
     * @param nid
     * @param uid
     * @return
     */
    @Override
    public Map<Integer, List<User>> getMyAllUsers(Integer nid, Integer uid) {
        List<User> userList = new ArrayList<>();
        Map<Integer, List<User>> datas = new HashMap<>();
        Network network = networkDao.getNetworkByUidAndId(uid, nid);
        List<UserNetwork> userNetworkList = userNetworkDao.findAllByNid(network.getId());
        for (int i = 0; i < userNetworkList.size(); i++) {
            Integer unid = userNetworkList.get(i).getId();
            UserNetwork userNetwork = userNetworkDao.getUserNetworkById(unid);
            Integer fid = userNetwork.getUid();
            //如果是自己,跳过
            if (fid == uid) {
                continue;
            }
            User user = userDao.getUserById(fid);
            userList.add(user);
        }
        if (userList.size() == 0) {
            datas.put(UserConstant.FAILED_CODE, userList);
        } else {
            datas.put(UserConstant.SUCCESS_CODE, userList);
        }
        return datas;
    }


    /**
     * 人脉网界面随机点击某个用户再列出该用户的所有好友
     *
     * @param user
     * @return
     */
    @Override
    public List<User> getNetworksUserInfo(User user) {
        List<User> userList = new ArrayList<>();
        Integer uid = user.getId();
        //得到自己所有的人脉圈
        List<Network> networkList = networkDao.findAllByUid(uid);
        if (networkList.size() > 0) {
            for (int i = 0; i < networkList.size(); i++) {
                Integer nid = networkList.get(i).getId();
                //得到每个人脉圈里所有的人
                List<UserNetwork> userNetworkList = userNetworkDao.findAllByNid(nid);
                if (userNetworkList.size() > 0) {
                    //循环遍历得出所有用户
                    for (int j = 0; j < userNetworkList.size(); j++) {
                        Integer unid = userNetworkList.get(i).getId();
                        UserNetwork userNetwork = userNetworkDao.getUserNetworkById(unid);
                        Integer fid = userNetwork.getUid();
                        //如果是自己,跳过
                        if (fid == uid) {
                            continue;
                        }
                        User user1 = userDao.getUserById(fid);
                        userList.add(user1);
                    }
                }
            }
        }
        return userList;
    }

    /**
     * 邀请更多人加入人脉圈
     *
     * @param user
     * @param network
     * @param session
     * @return
     */
    @Override
    public Map<Integer, String> inviteMoreUser(User user, Network network, HttpSession session) {
        Map<Integer, String> datas = new HashMap<>();
        //得到当前用户A的id
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        //得到要邀请人A人脉圈nid
        Integer nid = network.getId();
        //被邀请人B的id
        Integer sUserId = user.getId();//要被邀请进来的人B id

        //根据uid和nid查询出邀请人的当前朋友圈
        Network networkCenterUser = networkDao.getNetworkByUidAndId(uid, nid);
        if (!ObjectUtil.isEmpty(networkCenterUser)){//该人脉圈存在

            if (ObjectUtil.isEmpty(sUserId)) {//被邀请用户不存在!
                datas.put(NetworkConstant.FAIL_CODE, NetworkConstant.NOT_EXIST);
                return datas;
            } else if (userNetworkDao.getUserNetworkByUidAndNid(sUserId, nid) != null) {//用户已经加入
                datas.put(NetworkConstant.FAIL_CODE, NetworkConstant.ALREADY_EXIST);
                return datas;
            }
            //加入,维护关系
            createRelationBetwenNetworkAndFriends(nid,sUserId);
            datas.put(NetworkConstant.SUCCESS_CODE,NetworkConstant.SUCCESS);
        }
        return datas;
    }


    /**
     * 根据uid查询数据库中是否已经有该用户的三个默认朋友圈
     * 没有创建返回true
     * c创建返回:false
     *
     * @param uid
     * @return
     */
    @Override
    public boolean isNotCreateThreeCircle(Integer uid) {
        List<Network> networkList = networkDao.findAllByUid(uid);
        if (networkList.size() == 0) {//还没有
            return true;
        } else {
            //有了,判断名称
            Integer length = networkList.size();
            for (int i = 0; i < length; ++i) {
                Network network = networkList.get(i);
                String networkName = network.getNetworkName();
                if (networkName.equals(NetworkConstant.FRIENDCIRCLE)) {
                    return false;
                } else if (networkName.equals(NetworkConstant.ANSWERCIRCLR)) {
                    return false;
                } else if (networkName.equals(NetworkConstant.SPREADCIRCLE)) {
                    return false;
                }
            }
        }
        return true;
    }

}




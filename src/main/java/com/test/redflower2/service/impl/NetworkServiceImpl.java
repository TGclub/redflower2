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
     * @param session
     * @return
     */
    public Map<Integer, Object> createNetwork1(String networkName, String networkUrl, HttpSession session) {
        Map<Integer, Object> datas = new HashMap<>();
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        Network network1 = new Network();
        //若人脉圈名称为空，则创建失败
        if (ObjectUtil.isStringEmpty(networkName)) {
            datas.put(NetworkConstant.FAIL_CODE, NetworkConstant.NULL);
            return datas;
            //下面三句判断名称是否与默认人脉圈名称相同
        }else if (networkName.equals(NetworkConstant.FRIENDCIRCLE)){
            datas.put(NetworkConstant.FAIL_CODE,NetworkConstant.NAME_EXIST);
            return datas;
        }else if (networkName.equals(NetworkConstant.SPREADCIRCLE)){
            datas.put(NetworkConstant.FAIL_CODE,NetworkConstant.NAME_EXIST);
            return datas;
        }else if (networkName.equals(NetworkConstant.ANSWERCIRCLR)){
            datas.put(NetworkConstant.FAIL_CODE,NetworkConstant.NAME_EXIST);
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
        UserNetwork frienfUserNetwork = createRelationBetwenNetworkAndFriends(friendCircle.getId(), uid);
        networkDao.save(friendCircle);//保存
        userNetworkDao.save(frienfUserNetwork);
        idList.add(friendCircle.getId());

        //帮传播问题的人
        spreadQuesCircle.setUid(uid);
        spreadQuesCircle.setNetworkName(NetworkConstant.SPREADCIRCLE);
        UserNetwork spreadUsernetwork = createRelationBetwenNetworkAndFriends(spreadQuesCircle.getId(), uid);
        networkDao.save(spreadQuesCircle);
        userNetworkDao.save(spreadUsernetwork);
        idList.add(spreadQuesCircle.getId());

        //帮我回答问题的人
        answerQuesCirclr.setUid(uid);
        answerQuesCirclr.setNetworkName(NetworkConstant.ANSWERCIRCLR);
        UserNetwork answerUsernetwork = createRelationBetwenNetworkAndFriends(answerQuesCirclr.getId(), uid);
        networkDao.save(answerQuesCirclr);
        userNetworkDao.save(answerUsernetwork);
        idList.add(answerQuesCirclr.getId());
        return idList;
    }


    /**
     * 维护关系并保存
     *
     * @param fid
     * @return
     */
    public UserNetwork createRelationBetwenNetworkAndFriends(Integer nid, Integer fid) {
        UserNetwork userNetwork = new UserNetwork();
        userNetwork.setNid(nid);
        userNetwork.setFid(fid);
        userNetworkDao.save(userNetwork);
        return userNetwork;
    }

    /**
     *
     * done:查看我的人脉圈列表
     * 根据群主id查询出所有networK
     *
     * @param uid
     * @return
     */
    @Override
    public Map<Integer, List<Network>> getNetworksByUid(Integer uid) {


        Map<Integer, List<Network>> datas = new HashMap<>();
        List<Network> networkList = networkDao.findAllByUid(uid);

        if (networkList.size() == 0) {
            datas.put(NetworkConstant.FAIL_CODE, networkList);
            return datas;
        } else {
            for (int i = 0; i <networkList.size() ; ++i) {
                Network network = networkList.get(i);
                String networkName = network.getNetworkName();
                if (networkName.equals(NetworkConstant.FRIENDCIRCLE) && uid==network.getUid()){
                    networkList.add(0,network);

                }
                if (networkName.equals(NetworkConstant.SPREADCIRCLE) && uid==network.getUid()){
                    networkList.add(1,network);
                }

                if (networkName.equals(NetworkConstant.ANSWERCIRCLR) && uid==network.getUid()){
                    networkList.add(2,network);
                }
            }
            //还要啥操作
            datas.put(NetworkConstant.SUCCESS_CODE, networkList);
            return datas;
        }
    }


    /**
     * 获取每一个人脉圈的总人数
     * key:人脉圈名称  value:人脉圈中人数
     *
     * @param uid
     * @return
     */
    public Map<String, Integer> getMyNetworkUserSum(Integer uid) {
        Map<String, Integer> datas = new HashMap<>();
        //得到我的人脉圈总数
        Map<Integer, List<Network>> networkMapList = getNetworksByUid(uid);
        //人脉圈个数
        List<Network> networkList = networkMapList.get(NetworkConstant.SUCCESS_CODE);
        /**
         * 计算每个人脉圈人数
         */

        for (int i = 0; i < networkList.size(); ++i) {
            //装载个数
            List<Integer> count = new ArrayList<>();
            Integer nid = networkList.get(i).getId();
            Network network = networkDao.getNetworkById(nid);
            List<UserNetwork> userNetworkList = userNetworkDao.findAllByNid(nid);
            if (userNetworkList.size() == 0) {
                count.add(1);
            } else {
                int sum = 1;
                for (int j = 0; j < userNetworkList.size(); ++j) {
                    sum += sum;
                }
                count.add(sum);
            }
            datas.put(network.getNetworkName(), count.get(i));
        }
        return datas;
    }

    /**
     * 查询出某一个人脉圈中所有的人数
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
            Integer fid = userNetwork.getFid();
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
     * @param session
     * @return
     */
    @Override
    public Map<Integer, ?> getNetworksUserInfo(User user, HttpSession session) {
        List<User> userList = new ArrayList<>();
        Map<Integer, List<User>> datas = new HashMap<>();
        Integer uid = user.getId();
        List<Network> networkList = networkDao.findAllByUid(uid);
        for (int i = 0; i < networkList.size(); ++i) {
            Integer nid = networkList.get(i).getId();
            List<UserNetwork> userNetworkList = userNetworkDao.findAllByNid(nid);
            if (userNetworkList.size() != 0) {
                for (int j = 0; j < userNetworkList.size(); ++j) {
                    Integer unid = userNetworkList.get(i).getId();
                    UserNetwork userNetwork = userNetworkDao.getUserNetworkById(unid);
                    Integer fid = userNetwork.getFid();
                    //如果是自己,跳过
                    if (fid == uid) {
                        continue;
                    }
                    User user1 = userDao.getUserById(fid);
                    userList.add(user1);
                }
            }
        }
        if (userList.size() == 0) {
            datas.put(UserConstant.FAILED_CODE, null);
        } else {
            datas.put(UserConstant.SUCCESS_CODE, userList);
        }
        return datas;
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
        //得到当前用户B的id
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        //得到要邀请人B人脉圈nid
        Integer nid = network.getId();
        //根据uid和nid查询出邀请人的当前朋友圈
        Network networkCenterUser = networkDao.getNetworkByUidAndId(uid, nid);
        //维护关系
        UserNetwork userNetwork = new UserNetwork();

        Integer sUserId = user.getId();//要被邀请进来的人A id

        if (ObjectUtil.isEmpty(sUserId)) {//不存在该用户
            int status = NetworkConstant.FAIL_CODE;
            datas.put(status, NetworkConstant.NOT_EXIST);
            return datas;
        } else if (userNetworkDao.getUserNetworkByFidAndNid(sUserId, nid) != null) {//用户已经加入
            int status = NetworkConstant.FAIL_CODE;
            datas.put(status, NetworkConstant.ALREADY_EXIST);
            return datas;
        } else { //加入
            //绑定关系
            int status;
            userNetwork.setNid(nid);//被邀请人B人脉圈的nid,他们应该对应B的nid
            userNetwork.setFid(sUserId);
            userNetworkDao.save(userNetwork);
            //加入人脉圈
            boolean result = addUserIntoNetwork(networkCenterUser, user);
            if (result) {
                status = NetworkConstant.SUCCESS_CODE;
                datas.put(status, NetworkConstant.SUCCESS);
            } else {
                status = NetworkConstant.FAIL_CODE;
                datas.put(status, NetworkConstant.FAIL);
            }

        }
        return datas;
    }


    /**
     * 拓展我的人脉圈
     * 将user添加到network中
     *
     * @param network
     * @param user
     */
    public boolean addUserIntoNetwork(Network network, User user) {
        Network network1 = new Network();
        network1.setUid(user.getId());
        network1.setId(network.getId());
        network1.setNetworkUrl(network.getNetworkUrl());
        network1.setNetworkName(network.getNetworkName());
        networkDao.save(network1);
        return true;
    }


}




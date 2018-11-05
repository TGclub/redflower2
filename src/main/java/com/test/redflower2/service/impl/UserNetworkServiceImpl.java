package com.test.redflower2.service.impl;


import com.test.redflower2.constant.IntimacyConstant;
import com.test.redflower2.constant.NetworkConstant;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.dao.NetworkDao;
import com.test.redflower2.dao.UserDao;
import com.test.redflower2.dao.UserNetworkDao;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
public class UserNetworkServiceImpl implements UserNetworkService {

    private UserNetworkDao userNetworkDao;
    private NetworkDao networkDao;
    private UserDao userDao;

    @Autowired
    public UserNetworkServiceImpl(UserNetworkDao userNetworkDao, UserDao userDao, NetworkDao networkDao) {
        this.userNetworkDao = userNetworkDao;
        this.networkDao = networkDao;
        this.userDao = userDao;
    }


    /**
     * pass
     * 查看中心用户周围所有用户的信息
     *
     * @param user    其他用户
     * @param session
     * @return
     */
    @Override
    public List<User> getNetworkUserInfo1(User user, HttpSession session) {
        //装载用户人脉圈的圈子数
        List<Network> networkList = new ArrayList<>();
        //装载用户圈子里的人数
        List<User> userList = new ArrayList<>();

//        Integer uidCenter=(Integer) session.getAttribute(UserConstant.USER_ID);
        //中心用户
//        User userCenter = userDao.getUserById(uidCenter);

        //周围用户id
        Integer uidOther = user.getId();
        //通过要查询用户的id查询出所拥有的圈子数量
        List<UserNetwork> userNetworkList = userNetworkDao.getUserNetworkByUid(uidOther);
        //遍历查询一个id所对应的所有network
        if (userNetworkList.size() == 0) {
            return userList;//空
        } else {
            //不为空
            for (int i = 0; i < userNetworkList.size(); ++i) {
                Integer nid = userNetworkList.get(i).getNid();
                Network network = networkDao.getNetworkById(nid);
                networkList.add(network);
            }
        }
        //遍历用户的群．把所有好友装载到list中
        if (networkList.size() == 0) {
            return userList;
        } else {
            //不为空
            for (int i = 0; i < networkList.size(); ++i) {
                Integer userId = networkList.get(i).getUid();
                //跳过自己,不要把自己也装进去
                if (userId == uidOther) {
                    continue;
                }
                User userAll = userDao.getUserById(userId);
                userList.add(userAll);
            }
        }
        return userList;
    }

    /**
     * pass
     * done:查看我的人脉圈,
     *
     * @param uid
     * @return
     */
    @Override
    public Map<Integer, List<Network>> getNetworksByUid(Integer uid) {
        Map<Integer, List<Network>> datas = new HashMap<>();
        List<Network> networkList = new ArrayList<>();
        //把和用户相关的群全部查询出来
        List<UserNetwork> userNetworkList = userNetworkDao.getUserNetworkByUid(uid);
        if (userNetworkList.size() == 0) {
            //若为空,则该用户没有群
            int status;
            status = NetworkConstant.FAIL_CODE;
            datas.put(status, networkList);
            return datas;
        } else {
            //有,每个用户可能对应多个nid,即有多个朋友圈
            for (int i = 0; i < userNetworkList.size(); ++i) {
                //获取每一个朋友圈nid
                Integer nid = userNetworkList.get(i).getNid();
                Network network = networkDao.getNetworkById(nid);
                //将多个朋友圈放在list中
                networkList.add(network);
            }
            datas.put(NetworkConstant.SUCCESS_CODE, networkList);
            return datas;
        }
    }


    /**
     * 获取每一个人脉圈的总人数
     * key:人脉圈名称  value:r人脉圈中人数
     * @param uid
     * @return
     */
    public Map<String,Integer> getMyNetworkUserSum(Integer uid){
        Map<String,Integer> datas = new HashMap<>();
        //得到我的人脉圈
        Map<Integer,List<Network>> networkMapList = getNetworksByUid(uid);
        //人脉圈个数
        List<Network> networkList = networkMapList.get(NetworkConstant.SUCCESS_CODE);
        /**
         * 计算每个人脉圈人数
         */
        //遍历用户的群．把所有好友装载到list中
        //不为空
        for(int i = 0; i<networkList.size(); ++i) {
            //装载个数
            List<Integer> count = new ArrayList<>(2);
            //第n个人脉圈的id
            Integer nid = networkList.get(i).getId();
            Network network = networkDao.getNetworkById(nid);
            //该nid和uid所对应的关系数量为人数
            List<UserNetwork> userNetworkList1 = userNetworkDao.findAllByUidAndNid(uid, nid);
            int sum = 0;
            for (int j = 0; j < userNetworkList1.size(); j++) {
                sum += sum;
            }
            count.add(sum);
            datas.put(network.getNetworkName(), count.get(i));
        }
        return datas;
    }

    /**
     * 邀请更多人加入人脉网
     *
     * @param user
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
        } else if (userNetworkDao.getUserNetworkByUidAndNid(sUserId, nid) != null) {//用户已经加入
            int status = NetworkConstant.FAIL_CODE;
            datas.put(status, NetworkConstant.ALREADY_EXIST);
            return datas;
        } else { //加入
            //绑定关系
            int status;
            userNetwork.setNid(nid);//被邀请人B人脉圈的nid,他们应该对应B的nid
            userNetwork.setUid(sUserId);
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
            return datas;
        }
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


    /**
     * 显示人脉网界面随机某一个用户的信息，并且显示与主用户的亲密度
     *
     * @param userForm 要查看的用户：只能查看中心用户周围的用户与中心用户的亲密度
     * @param session
     * @return 亲密读关系, 先作废
     */
    @Override
    public Map<Integer, Object> getNetworkUserInfo(User userForm, HttpSession session) {
        Map<Integer, Object> datas = new HashMap<>();
        List<Object> userList = new ArrayList<>(2);
        //保存亲密度关系
        Intimacy intimacy = new Intimacy();
        Integer userFormId = userForm.getId();
        User sUser = userDao.getUserById(userFormId);
        if (ObjectUtil.isEmpty(sUser)) {
            datas.put(NetworkConstant.FAIL_CODE, NetworkConstant.NOT_EXIST);
        }

        //获取要查看用户的亲密度阈值和中心用户进行比较以分辨亲密度
        Integer formValue = userForm.getValue();
        Integer userId = (Integer) session.getAttribute(UserConstant.USER_ID);
        User user = userDao.getUserById(userId);//中心用户
        Integer userValue = user.getValue();
        boolean result = compareIntimacyValue(formValue, userValue, IntimacyConstant.BOUNDARY);
        if (result) {//亲密的话，保存在Intimacy表中，维持这两个用户的关系
            intimacy.setUserValue(userValue);
            intimacy.setFormValue(formValue);
            intimacy.setResult(IntimacyConstant.HIGH);
            //亲密结果加入list[0]
            userList.add(intimacy.getResult());
        } else {
            //不亲密的话，保存在Intimacy表中，维持这两个用户的关系
            intimacy.setFormValue(formValue);
            intimacy.setUserValue(userValue);
            intimacy.setResult(IntimacyConstant.LOW);
            //不亲密结果加入list[0]
            userList.add(intimacy.getResult());
        }
        //加入list[1]
        userList.add(sUser);
        datas.put(UserConstant.SUCCESS_CODE, userList);
        return datas;
    }

    /**
     * 比较两个用户的亲密度
     *
     * @param formValue
     * @param userValue
     * @param boundary
     * @return 初步算法：两者的差值>10   不亲密
     * <10    亲密
     */


    private boolean compareIntimacyValue(Integer formValue, Integer userValue, int boundary) {
        //被查用户比较
        int result = ObjectUtil.abs(formValue - userValue);
        if (result < boundary) {//亲密
            return true;
        } else {//不亲密
            return false;
        }
    }



}

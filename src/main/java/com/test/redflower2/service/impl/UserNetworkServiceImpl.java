package com.test.redflower2.service.impl;


import com.alibaba.fastjson.JSONObject;
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
    public UserNetworkServiceImpl(UserNetworkDao userNetworkDao, UserDao userDao,NetworkDao networkDao) {
        this.userNetworkDao = userNetworkDao;
        this.networkDao = networkDao;
        this.userDao=userDao;
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
     * 查看我的人脉圈
     * @param uid
     * @return
     */
    @Override
    public Map<String, List<Network>> getNetworksByUid(Integer uid) {
        Map<String,List<Network>> datas = new HashMap<>();
        List<Network> networkList = new ArrayList<>();
        List<UserNetwork> userNetworkList = userNetworkDao.getUserNetworksByUid(uid);
        if (userNetworkList.size()==0){
            //此处该返回什么,可能有问题
            datas.put(NetworkConstant.NOT_HAVE_NETWORK,networkList);
            return datas;
        }else {
            //有
            for (int i = 0; i <userNetworkList.size() ; ++i) {
                //获取nid
                Integer nid = userNetworkList.get(i).getNid();
                Network network = networkDao.getNetworkById(nid);
                networkList.add(network);
            }
            datas.put(NetworkConstant.SUCCESS,networkList);
            return datas;
        }
    }

    /**
     * 邀请更多人加入人脉网
     * @param user
     * @param session
     * @return
     */
    @Override
    public Map<Integer, String> inviteMoreUser(User user, HttpSession session) {
        Map<Integer, String> datas = new HashMap<>();
        Integer uid =(Integer) session.getAttribute(UserConstant.USER_ID);//得到当前用户B的id
        Network network = networkDao.getNetworkByUid(uid);//查询出当前用户人B人脉圈nid
        Integer nid = network.getId();//B人脉圈的nid
        UserNetwork userNetwork =new UserNetwork();
        Integer sUserId = user.getId();//要被邀请进来的人A id

        if (ObjectUtil.isEmpty(sUserId)){//不存在该用户
            int status =NetworkConstant.FAIL_CODE;
            datas.put(status,NetworkConstant.NOT_EXIST);
            return datas;
        } else if (userNetworkDao.getUserNetworkByUidAndNid(sUserId, nid) != null) {//用户已经加入
            int status=NetworkConstant.FAIL_CODE;
            datas.put(status,NetworkConstant.ALREADY_EXIST);
            return datas;
        } else { //加入
            int status;
            userNetwork.setNid(nid);//被邀请人B人脉圈的nid,他们应该对应B的nid
            userNetwork.setUid(sUserId);
            userNetworkDao.save(userNetwork);
            status=NetworkConstant.SUCCESS_CODE;
            datas.put(status,NetworkConstant.SUCCESS);
            return datas;
        }
    }


    /**
     * 显示人脉网界面随机某一个用户的信息，并且显示与主用户的亲密度
     *
     * @param userForm 要查看的用户：只能查看中心用户周围的用户与中心用户的亲密度
     * @param session
     * @return
     */
    @Override
    public Map<Integer, Object> getNetworkUserInfo(User userForm, HttpSession session) {
        Map<Integer, Object> datas = new HashMap<>();
        List<Object> userList = new ArrayList<>(2);
        //保存亲密度关系
        Intimacy intimacy =  new Intimacy();
        Integer userFormId = userForm.getId();
        User sUser = userDao.getUserById(userFormId);
        if (ObjectUtil.isEmpty(sUser)){
            datas.put(NetworkConstant.FAIL_CODE,NetworkConstant.NOT_EXIST);
        }

        //获取要查看用户的亲密度阈值和中心用户进行比较以分辨亲密度
        Integer formValue=userForm.getValue();
        Integer userId = (Integer) session.getAttribute(UserConstant.USER_ID);
        User user = userDao.getUserById(userId);//中心用户
        Integer userValue = user.getValue();
        boolean result = compareIntimacyValue(formValue,userValue, IntimacyConstant.BOUNDARY);
        if (result){//亲密的话，保存在Intimacy表中，维持这两个用户的关系
            intimacy.setUserValue(userValue);
            intimacy.setFormValue(formValue);
            intimacy.setResult(IntimacyConstant.QINMI);
            //加入list[0]
            userList.add(intimacy);
        }else {
            //不亲密的话，保存在Intimacy表中，维持这两个用户的关系
            intimacy.setFormValue(formValue);
            intimacy.setUserValue(userValue);
            intimacy.setResult(IntimacyConstant.NOT_QINMI);
            //加入list[0]
            userList.add(intimacy);
        }
        //加入list[1]
        userList.add(sUser);
        datas.put(UserConstant.SUCCESS_CODE, userList);
        return datas;
    }

    /**
     * 比较两个用户的亲密度
     * @param formValue
     * @param userValue
     * @param boundary
     * @return
     * 初步算法：两者的差值>10   不亲密
     *                    <10    亲密
     */


    private boolean compareIntimacyValue(Integer formValue, Integer userValue, int boundary) {
        //被查用户比较
        int result =ObjectUtil.abs(formValue-userValue);
        if (result<boundary){//亲密
            return true;
        }else {//不亲密
            return false;
        }
    }
}

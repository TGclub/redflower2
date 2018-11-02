package com.test.redflower2.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.redflower2.annotation.Authorization;
import com.test.redflower2.constant.NetworkConstant;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.enums.InfoStatusEnum;
import com.test.redflower2.pojo.dto.*;
import com.test.redflower2.pojo.entity.Intimacy;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.pojo.entity.UserNetwork;
import com.test.redflower2.service.IntimacyService;
import com.test.redflower2.service.NetworkService;
import com.test.redflower2.service.UserNetworkService;
import com.test.redflower2.service.UserService;
import com.test.redflower2.utils.ObjectUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 人脉网
 */
@RestController
@RequestMapping(value = "/network")
public class NetworkController {

    private NetworkService networkService;

    private UserNetworkService userNetworkService;

    private UserService userService;

    private IntimacyService intimacyService;

    @Autowired
    public NetworkController(NetworkService networkService, UserNetworkService userNetworkService, UserService userService, IntimacyService intimacyService) {
        this.networkService = networkService;
        this.userNetworkService = userNetworkService;
        this.userService = userService;
        this.intimacyService = intimacyService;
    }

    /**
     * 创建新的人脉圈
     *
     * @Param name
     */
    @ApiOperation(value = NetworkConstant.NEW_NETWORK,httpMethod = "POST")
    @PostMapping(value = "/createNetwork")
    public Result<Object> addNetWork(@RequestParam("networkName")String networkName,
                                     HttpSession session){
        Map<String,Integer> datas=networkService.createNetwork(networkName,session);
        return ResultBuilder.success(datas);
    }

    /**
     * 拓展我的人脉圈
     * 邀请更多人加入人脉网 一个人脉圈对应多个用户
     * @param user 被邀请user
     * @param session
     * @return
     */
    @PostMapping("/inviteUser")
    public Result<Object> inviteMoreUsers(@RequestBody User user,HttpSession session){
        Map<String,Integer> datas = userNetworkService.inviteMoreUser(user,session);
        return ResultBuilder.success(datas);
    }


    /**
     * 查看我的人脉圈
     * @param session
     * @return
     */
    @GetMapping("/getMyNetworks")
    public Result<Object> getMyNetwork(HttpSession session){
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        if (ObjectUtil.isEmpty(uid)){
            return ResultBuilder.fail(NetworkConstant.NOT_LOGIN);
        }else {
            Map<String, List<Network>> networkList  = userNetworkService.getNetworksByUid(uid);
            return ResultBuilder.success(networkList);
        }
    }



    /**
     * 人脉网界面随机点击用户的个人信息显示,要显示与主用户之间的亲密度：默认不亲密
     * @param user
     * @return
     */
    @PostMapping("/getUserInfo")
    public Result<Object> getUserInfo(@RequestBody User user,HttpSession session){
        Map<String,List<User>> datas = userService.getNetworkUserInfo(user,session);
        return ResultBuilder.success(datas);
    }


//    /**
 //     * 人脉网界面
 //     *
 //     * @param userId
 //     * @return
 //     */
//    @RequestMapping(value = "/image", method = RequestMethod.POST)
//    public ResponseDto getDifferentUser(@RequestParam("userId") Integer userId,
//                                        HttpSession httpSession) {
//        if (httpSession.getAttribute("userId") == null) {
//            return ResponseDto.failed("no login");
//        }
//
//        if (userService.getUserById(userId) == null) {
//            return ResponseDto.failed("no userId");
//        }
//
//        List<UserNetwork> userNetworkList = userNetworkService.getUserNetworksByNid(userId);
//        if (userNetworkList.isEmpty()) {
//            return ResponseDto.failed("you do not have network");
//        }
//
//        List<Integer> nids = new ArrayList<>();
//        for (int i = 0; i < userNetworkList.size(); i++) {
//            nids.add(userNetworkList.get(i).getNid());
//        }
//
//        //uids为userId所在所有人脉圈中所有人的id
//        Set<Integer> uids = new HashSet<>();
//        for (int i = 0; i < nids.size(); i++) {
//            List<UserNetwork> userNetworkListBynidsI = userNetworkService.getUserNetworksByNid(nids.get(i));
//            for (int j = 0; j < userNetworkListBynidsI.size(); j++) {
//                uids.add(userNetworkListBynidsI.get(j).getUid());
//            }
//        }
//        //low亲密度的uid
//        List<Integer> lowUids = new ArrayList<>();
//        //high亲密度的uid
//        List<Integer> highUids = new ArrayList<>();
//
//        for (Integer uid : uids) {
//            //userid和uid之间的亲密度
//            Integer intimacyValue = intimacyService.getIntimacyByUid1AndUid2(uid, userId).getValue();
//            if (intimacyValue < InfoStatusEnum.rank.getValue()) {
//                Integer temp = uid;
//                lowUids.add(temp);
//            } else {
//                Integer temp = uid;
//                highUids.add(temp);
//            }
//        }
//
//        JSONObject mainUser = new JSONObject();
//        mainUser.put(userService.getUserById(userId).getId().toString(), userService.getUserById(userId).getAvatarUrl());
//
//        Random random = new Random();
//        JSONObject maxUser = new JSONObject();
//        //highuids小于4，全放进Json
//        if (highUids.size() < 4) {
//            for (Integer uid : highUids) {
//                maxUser.put(userService.getUserById(highUids.get(uid)).getId().toString(),
//                        userService.getUserById(highUids.get(uid)).getAvatarUrl());
//            }
//        } else {
//            /**
//             * 在highuids中随机取三个uid
//             */
//            //取3个不大于highuids.size的随机数
//            Set<Integer> rands = new HashSet<>();
//            while (rands.size() < 4) {
//                rands.add(random.nextInt(highUids.size()));
//            }
//            for (Integer rand : rands) {
//                maxUser.put(userService.getUserById(highUids.get(rand)).getId().toString(),
//                        userService.getUserById(highUids.get(rand)).getAvatarUrl());
//            }
//        }
//
//        JSONObject minUser = new JSONObject();
//        //lowuids小于7，全放进Json
//        if (lowUids.size() < 7) {
//            for (Integer uid : lowUids) {
//                minUser.put(userService.getUserById(lowUids.get(uid)).getId().toString(),
//                        userService.getUserById(lowUids.get(uid)).getAvatarUrl());
//            }
//        } else {
//            /**
//             * 在lowuids中随机取6个uid
//             */
//            //取6个不大于lowuids.size的随机数
//            Set<Integer> rands = new HashSet<>();
//            while (rands.size() < 7) {
//                rands.add(random.nextInt(lowUids.size()));
//            }
//            for (Integer rand : rands) {
//                minUser.put(userService.getUserById(lowUids.get(rand)).getId().toString(),
//                        userService.getUserById(lowUids.get(rand)).getAvatarUrl());
//            }
//        }
//
//        NetWorkDto netWorkDto = new NetWorkDto(mainUser, maxUser, minUser);
//        return ResponseDto.succeed("succeed", netWorkDto);
//    }
//

    /**
     * 测试
     * @return
     */
    @Authorization
    @GetMapping("/test")
    public Result<Object> test(){
        return ResultBuilder.success();
    }
}

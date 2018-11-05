package com.test.redflower2.web.controller;

import com.test.redflower2.annotation.Authorization;
import com.test.redflower2.constant.NetworkConstant;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.pojo.dto.*;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.IntimacyService;
import com.test.redflower2.service.NetworkService;
import com.test.redflower2.service.UserNetworkService;
import com.test.redflower2.service.UserService;
import com.test.redflower2.utils.ObjectUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 人脉网
 */
@RestController
@RequestMapping(value = "/network")
public class NetworkController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private NetworkService networkService;

    private UserNetworkService userNetworkService;



    @Autowired
    public NetworkController(NetworkService networkService, UserNetworkService userNetworkService, UserService userService, IntimacyService intimacyService) {
        this.networkService = networkService;
        this.userNetworkService = userNetworkService;
    }

//
//    /**
//     * 创建新的人脉圈
//     *
//     * @Param name
//     */
//    @ApiOperation(value = NetworkConstant.NEW_NETWORK, httpMethod = "POST")
//    @PostMapping(value = "/createNetwork")
//    public Result<Object> addNetWork(@RequestParam("networkName") String networkName,
//                                     HttpSession session) {
//        Map<Integer, String> datas = networkService.createNetwork(networkName, session);
//        //创建失败，返回失败信息
//        if (!ObjectUtil.isStringEmpty(datas.get(NetworkConstant.FAIL_CODE))) {
//            return ResultBuilder.fail(datas.get(NetworkConstant.FAIL_CODE));
//        }
//        return ResultBuilder.success();
//    }


    /**
     * 创建新的人脉圈
     * @param networkName
     * @param networkUrl
     * @param session
     * @return
     */
    @ApiOperation(value = NetworkConstant.NEW_NETWORK, httpMethod = "POST")
    @PostMapping(value = "/createNetwork")
    public Result<Object> addNetWork(@RequestParam("networkName") String networkName,
                                     @RequestParam("networkUrl")String networkUrl,
                                     HttpSession session) {
        logger.info("network "+" time "+System.currentTimeMillis());
        System.out.println("network:"+networkName+" networkUrl"+networkUrl);
//        String networkName=params.get("networkName").toString();
//        String networkUrl=params.get("networkUrl").toString();
        Map<Integer, String> datas = networkService.createNetwork1(networkName,networkUrl, session);
        //创建失败，返回失败信息
        if (!ObjectUtil.isStringEmpty(datas.get(NetworkConstant.FAIL_CODE))) {
            return ResultBuilder.fail(datas.get(NetworkConstant.FAIL_CODE));
        }
        return ResultBuilder.success();
    }


    /**
     * 拓展我的人脉圈
     * 邀请更多人加入人脉网 一个人脉圈对应多个用户
     *
     * @param user    被邀请user
     * @param session
     * @return
     */
    @PostMapping("/inviteUser")
    public Result<Object> inviteMoreUsers(@RequestBody User user,Network network,
                                          HttpSession session) {
        logger.info("user :"+user+" time "+System.currentTimeMillis());
        Map<Integer, String> datas = userNetworkService.inviteMoreUser(user,network, session);
        //添加失败
        if (!ObjectUtil.isStringEmpty(datas.get(NetworkConstant.FAIL_CODE))) {
            return ResultBuilder.fail(datas.get(NetworkConstant.FAIL_CODE));
        }
        return ResultBuilder.success();
    }


    /**
     * pass
     * 查看我的人脉圈
     *
     * @param session
     * @return
     */
    @GetMapping("/getMyNetworks")
    public Result<Object> getMyNetwork(HttpSession session) {
        logger.info("查看我的人脉圈session :"+session+" time "+System.currentTimeMillis());
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        if (ObjectUtil.isEmpty(uid)) {
            return ResultBuilder.fail(NetworkConstant.NOT_LOGIN);
        } else {
            Map<Integer, List<Network>> networkList = userNetworkService.getNetworksByUid(uid);
            return ResultBuilder.success(networkList.get(NetworkConstant.SUCCESS_CODE));
        }
    }


    /**
     * 查看我的人脉圈所对应的人数
     * @param uid
     * @return
     */
    @PostMapping("/getMyNetworksUserCount")
    public Result<Object> getMyNetworksUserCount(@RequestParam Integer uid){
        Map<String,Integer> datas = userNetworkService.getMyNetworkUserSum(uid);
        return ResultBuilder.success(datas);
    }



    /**
     * pass
     * 人脉网界面随机点击用户的个人信息显示,要显示与主用户之间的亲密度：默认不亲密(前端暂时处理)
     * @param user
     * @return
     */
    @PostMapping("/getUserInfo")
    public Result<Object> getUserInfo(@RequestBody User user,HttpSession session){
        logger.info("user :"+user+" time "+System.currentTimeMillis());
        List<User> userList = userNetworkService.getNetworkUserInfo1(user,session);
        if (userList.size()==0){
            return ResultBuilder.fail("列表为空,还没有好友!");
        }else {
            return ResultBuilder.success(userList);
        }
    }


    /**
     * 人脉网界面
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/userNetworkImage")
    public Result<Object> userNetWorkImage(@RequestBody User user,HttpSession session){
        logger.info("user :"+user+" time "+System.currentTimeMillis());
        return null;
    }


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

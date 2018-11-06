package com.test.redflower2.controller;

import com.test.redflower2.annotation.Authorization;
import com.test.redflower2.constant.NetworkConstant;
import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.pojo.dto.*;
import com.test.redflower2.pojo.entity.Network;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.NetworkService;
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

    private UserService userService;


    @Autowired
    public NetworkController(NetworkService networkService, UserService userService) {
        this.networkService = networkService;
        this.userService = userService;
    }


    /**
     * 创建新的人脉圈
     *
     * @param networkName
     * @param networkUrl
     * @param session
     * @return
     */
    @ApiOperation(value = NetworkConstant.NEW_NETWORK, httpMethod = "POST")
    @PostMapping(value = "/createNetwork")
    public Result<Object> addNetWork(@RequestParam("networkName") String networkName,
                                     @RequestParam("networkUrl") String networkUrl,
                                     HttpSession session) {
        logger.info("network " + " time " + System.currentTimeMillis());
        System.out.println("network:" + networkName + " networkUrl" + networkUrl);
        Map<Integer, Object> datas = networkService.createNetwork1(networkName, networkUrl, session);
        //创建失败，返回失败信息
        if (!ObjectUtil.isEmpty(datas.get(NetworkConstant.FAIL_CODE))) {
            return ResultBuilder.fail(datas.get(NetworkConstant.FAIL_CODE).toString());
        }
        //返回nid
        return ResultBuilder.success(datas.get(NetworkConstant.SUCCESS_CODE));
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
    public Result<Object> inviteMoreUsers(@RequestBody User user, Network network,
                                          HttpSession session) {
        logger.info("user :" + user + " time " + System.currentTimeMillis());
        Map<Integer, String> datas = networkService.inviteMoreUser(user, network, session);
        //添加失败
        if (!ObjectUtil.isStringEmpty(datas.get(NetworkConstant.FAIL_CODE))) {
            return ResultBuilder.fail(datas.get(NetworkConstant.FAIL_CODE));
        }
        return ResultBuilder.success();
    }


    /**
     * 查看我的人脉圈,列出人脉圈的列表
     *
     * @param session
     * @return
     */
    @GetMapping("/getMyNetworks")
    public Result<Object> getMyNetwork(HttpSession session) {
        logger.info("查看我的人脉圈session :" + session + " time " + System.currentTimeMillis());
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        if (ObjectUtil.isEmpty(uid)) {
            return ResultBuilder.fail(NetworkConstant.NOT_LOGIN);
        } else {
            Map<Integer, List<Network>> networkList = networkService.getNetworksByUid(uid);
            return ResultBuilder.success(networkList.get(NetworkConstant.SUCCESS_CODE));
        }
    }


    /**
     * 查看我的各个人脉圈所对应的人数
     *
     * @param
     * @return
     */
    @GetMapping("/getMyNetworksUserCount")
    public Result<Object> getMyNetworksUserCount(HttpSession session) {
        logger.info("查看我的各个人脉圈所对应的人数:{}" + " time " + System.currentTimeMillis());
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        Map<String, Integer> datas = networkService.getMyNetworkUserSum(uid);
        return ResultBuilder.success(datas);
    }


    /**
     * pass
     * 人脉网界面随机点击用户的得到其所有人的人脉
     *
     * @param user
     * @return
     */
    @PostMapping("/getUserInfo")
    public Result<Object> getUserInfo(@RequestBody User user, HttpSession session) {
        logger.info("user :" + user + " time " + System.currentTimeMillis());
        Map<Integer, List<User>> userList = networkService.getNetworksUserInfo(user, session);
        if (userList.size() == 0) {
            return ResultBuilder.fail("列表为空,还没有好友!");
        } else {
            return ResultBuilder.success(userList);
        }
    }


    /**
     * 点击进入某一个人脉圈,显示我的所有好友
     *
     * @return
     */
    @GetMapping("/getMyAllUsers")
    public Result<Object> getMyAllUsers(@RequestParam("nid") Integer nid, HttpSession session) {
        logger.info("点击进入某一个人脉圈,显示我的所有好友:{}" + " time " + System.currentTimeMillis());
        Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
        Map<Integer, List<User>> datas = networkService.getMyAllUsers(nid, uid);
        //为空
        if (!ObjectUtil.isEmpty(datas.get(UserConstant.FAILED_CODE))) {
            return ResultBuilder.success(UserConstant.FAILED_CODE);
        } else {
            return ResultBuilder.success(datas.get(UserConstant.SUCCESS_CODE));
        }
    }

    /**
     * 进入人脉网后查看我周围某个用户的信息
     *
     * @param uid
     * @return
     */
    @GetMapping("/getOneUserInfo")
    public Result<Object> getOneUserInfo(@RequestParam("uid") Integer uid) {
        logger.info("进入人脉网后查看我周围某个用户的信息:{}" + " time " + System.currentTimeMillis());
        Map<Integer, User> datas = userService.getOneUserInfo(uid);
        if (!ObjectUtil.isEmpty(datas.get(UserConstant.FAILED_CODE))) {
            return ResultBuilder.fail(UserConstant.USER_NOT_EXIST);
        } else {
            return ResultBuilder.success(datas.get(UserConstant.SUCCESS_CODE));
        }
    }


    /**
     * 测试
     *
     * @return
     */
    @Authorization
    @GetMapping("/test")
    public Result<Object> test() {
        return ResultBuilder.success();
    }
}

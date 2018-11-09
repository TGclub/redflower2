package com.test.redflower2.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
     * 测试ok
     * 创建人脉圈
     * @param json
     * @param session
     * @return
     */
    @ApiOperation(value = NetworkConstant.NEW_NETWORK, httpMethod = "POST")
    @PostMapping (value = "/createNetwork")
    public Result<Object> addNetWork(@RequestBody String json,
                                     HttpSession session) {
        logger.info("创建人脉圈: " + " time " + System.currentTimeMillis());
        if (ObjectUtil.isStringEmpty(json)) {
            return ResultBuilder.fail(NetworkConstant.PARAMS_NULL);
        }
        String networkName,networkUrl;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            networkName = jsonObject.getString("networkName");
            networkUrl=jsonObject.getString("networkUrl");
        } catch (Exception e) {
            System.out.println(NetworkConstant.NETWORK_WRONG);//打印查错
            return ResultBuilder.fail(NetworkConstant.NETWORK_WRONG);
        }
        Map<Integer, Object> datas = networkService.createNetwork1(networkName, networkUrl, session);
        //创建失败，返回失败信息
        if (!ObjectUtil.isEmpty(datas.get(NetworkConstant.FAIL_CODE))) {
            return ResultBuilder.fail((String.valueOf(datas.get(NetworkConstant.FAIL_CODE))));//.toString(),Interger转String后台日志报错;String.valueOf(),可以将interger转换为String,(String)不能
        }
        //返回nid
        return ResultBuilder.success(datas.get(NetworkConstant.SUCCESS_CODE));
    }


    /**
     * 邀请更多成员
     * @param json    被邀请user
     * @param session
     * @return
     */
    @PostMapping("/inviteUser")
    public Result<Object> inviteMoreUsers(@RequestBody String json,
                                          HttpSession session) {
        logger.info("邀请更多成员 :" + json + " time " + System.currentTimeMillis());
        JSONObject jsonObject = JSON.parseObject(json);
        Integer uid = jsonObject.getInteger("uid");
        Integer nid = jsonObject.getInteger("nid");
        Map<Integer, String> datas = networkService.inviteMoreUser(uid, nid, session);
        //添加失败
        if (!ObjectUtil.isStringEmpty(datas.get(NetworkConstant.FAIL_CODE))) {
            return ResultBuilder.fail(datas.get(NetworkConstant.FAIL_CODE));
        }
        return ResultBuilder.success();
    }


    /**
     * 测试ok
     * 查看我的人脉圈,列出人脉圈的列表   以及每个人脉圈的人数
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
            List<Object> objectList = networkService.getNetworksAndCountByUid(uid);
            return ResultBuilder.success(objectList);
        }
    }


    /**
     * 测试ok
     * 人脉网界面随机点击用户得到其所有的人脉
     * @param json
     * @return
     */
    @PostMapping("/getUserInfo")
    public Result<Object> getUserInfo(@RequestBody  String json) {
        logger.info("getUserInfo :" + json + " time " + System.currentTimeMillis());
        JSONObject jsonObject = JSON.parseObject(json);
        Integer uid = jsonObject.getInteger("uid");
        logger.info("查看人脉网界面某一个用户所有人脉的uid为:"+uid+" time "+System.currentTimeMillis());
        Map<Integer,List<User>> datas = networkService.getNetworksUserInfo(uid);
        //为空
        if (!ObjectUtil.isEmpty(datas.get(UserConstant.FAILED_CODE))) {
            return ResultBuilder.success(datas.get(UserConstant.FAILED_CODE));//返回空列表
        } else {
            return ResultBuilder.success(datas.get(UserConstant.SUCCESS_CODE));//返回有数剧列表
        }
    }


    /**
     * 测试ok
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
            return ResultBuilder.success(datas.get(UserConstant.FAILED_CODE));//返回空列表
        } else {
            return ResultBuilder.success(datas.get(UserConstant.SUCCESS_CODE));//返回有数剧列表
        }
    }

    /**
     *
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
     * 自测试
     *
     * @return
     */
    @Authorization
    @GetMapping("/test")
    public Result<Object> test() {
        return ResultBuilder.success();
    }
}

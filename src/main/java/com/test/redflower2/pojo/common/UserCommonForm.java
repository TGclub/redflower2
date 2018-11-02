package com.test.redflower2.pojo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.redflower2.constant.IntimacyConstant;

/**
 * 用户前台数据映射表
 */
public class UserCommonForm {
    /**
     * 用户姓名
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;

    /**
     * 用户性别
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int gender;

    /**
     * 用户个性签名
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String definition;

    /**
     * 用户微信id
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String wxid;

    /**
     * 头像
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String avatarUrl;

    /**
     * 用户和用户之间亲密度阈值
     */
    @JsonIgnore
    private Integer state = IntimacyConstant.BOUNDARY;




}

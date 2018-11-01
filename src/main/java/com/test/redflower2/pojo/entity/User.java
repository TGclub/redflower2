package com.test.redflower2.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.redflower2.enums.UserInfoStateEnum;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer id;

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
     * openid
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String openid;

    /**
     * 头像
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String avatarUrl;

    /**
     * 记录用户状态信息
     */
    @JsonIgnore
    private Integer state = UserInfoStateEnum.INCOMPLETED.getValue();


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", definition='" + definition + '\'' +
                ", wxid='" + wxid + '\'' +
                ", openid='" + openid + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", state=" + state +
                '}';
    }
}


package com.test.redflower2.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer id;

    /**
     * 用户微信名
     */
    private String name;////微信可得到

    /**
     * 用户性别
     * 0:男
     * 1:女
     */
    private int gender;  //微信可得到

    /**
     * 用户个性签名
     */
    private String definition = "definition";


    /**
     * openid
     */
    //当Value 为null 不输出
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String openid;

    /**
     * 头像
     */
    private String avatarUrl;  //微信可得到


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
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}


package com.test.redflower2.pojo.dto;

/**
 * 返回给前端的信息
 */
public class  ProfileDto {


    /**
     * 用户id
     */
    private Integer uid ;

    /**
     *
     * 用户名称
     */
    private String name;

    /**
     * 用户个性签名
     */
    private String definition;

    /**
     * 微信号
     */
    private String wx;

    /**
     * 用户性别
     */
    private Integer gender;


    public ProfileDto(){}

    public ProfileDto(String name, String definition, String wx){
        this.name = name;
        this.definition = definition;
        this.wx = wx;
    }



    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ProfileDto{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", definition='" + definition + '\'' +
                ", wx='" + wx + '\'' +
                ", gender=" + gender +
                '}';
    }
}

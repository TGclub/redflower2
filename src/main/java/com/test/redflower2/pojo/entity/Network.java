package com.test.redflower2.pojo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 人脉圈实体类
 */
@Entity
@Table(name = "network")
public class Network {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * 人脉圈id
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer id;

    /**
     * 用户id
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer uid;

    /**
     * 人脉圈名称
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String networkName;

    /**
     *
     * 人脉圈头像
     */
    private String networkUrl;


    /**
     * 人脉圈总人数,默认是1
     */
    private Integer count= Integer.MIN_VALUE ;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNetworkUrl() {
        return networkUrl;
    }

    public void setNetworkUrl(String networkUrl) {
        this.networkUrl = networkUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Network{" +
                "id=" + id +
                ", uid=" + uid +
                ", networkName='" + networkName + '\'' +
                ", networkUrl='" + networkUrl + '\'' +
                ", count=" + count +
                '}';
    }
}

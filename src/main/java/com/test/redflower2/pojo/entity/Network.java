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
    private Integer uid;

    /**
     * 人脉圈名称
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String networkName;

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
                '}';
    }
}

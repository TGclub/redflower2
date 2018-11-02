package com.test.redflower2.pojo.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_network")
public class UserNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    /**
     * 人脉圈里的各个用户id
     */
    private Integer uid;

    /**
     * 用户所属于的人脉圈id
     */
    private Integer nid;

    public Integer getId() {
        return id;
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

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    @Override
    public String
    toString() {
        return "UserNetwork{" +
                "id=" + id +
                ", uid=" + uid +
                ", nid=" + nid +
                '}';
    }
}

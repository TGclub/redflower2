package com.test.redflower2.pojo.entity;

import javax.persistence.*;

/**
 * 这个类是关系表,群的id,和群友id
 */
@Entity
@Table(name = "user_network")
public class UserNetwork {

    /**
     * 关系表Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 群里的各个用户id,包括群主在内
     */
    private Integer fid;


    /**
     * 群友所属于的人脉圈id
     */
    private Integer nid;


    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
                ", fid=" + fid +
                ", nid=" + nid +
                '}';
    }
}

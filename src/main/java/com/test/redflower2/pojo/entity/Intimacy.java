package com.test.redflower2.pojo.entity;

import javax.persistence.*;

@Entity
@Table(name = "intimacy")
public class Intimacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uid1;

    private Integer uid2;

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getUid2() {
        return uid2;

    }

    public void setUid2(Integer uid2) {
        this.uid2 = uid2;
    }

    public Integer getUid1() {

        return uid1;
    }

    public void setUid1(Integer uid1) {
        this.uid1 = uid1;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Intimacy{" +
                "id=" + id +
                ", uid1=" + uid1 +
                ", uid2=" + uid2 +
                ", value=" + value +
                '}';
    }
}

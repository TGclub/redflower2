package com.test.redflower2.pojo.entity;

import com.test.redflower2.constant.IntimacyConstant;

import javax.persistence.*;

/**
 * 亲密度:分两种，亲密和不亲密，现在默认都是不亲密，以用户为中心，周围都是小头像
 */

/**
 * 两个用户之间亲密度维护关系表
 */
@Entity
@Table(name = "intimacy")
public class Intimacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 中心用户亲密度值
     */
    private Integer userValue;

    /**
     * 被查看用户的亲密度值
     */
    private Integer formValue;

    /**
     * 两个用户亲密度值比较之后的最终结果
     */
    private String result ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserValue() {
        return userValue;
    }

    public void setUserValue(Integer userValue) {
        this.userValue = userValue;
    }

    public Integer getFormValue() {
        return formValue;
    }

    public void setFormValue(Integer formValue) {
        this.formValue = formValue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Intimacy{" +
                "id=" + id +
                ", userValue=" + userValue +
                ", formValue=" + formValue +
                ", result='" + result + '\'' +
                '}';
    }
}

package com.test.redflower2.pojo.dto;

public class NetWorkDto {
    Object mainUser;
    Object maxUser;
    Object minUser;

    public NetWorkDto(Object mainUser, Object maxUser, Object minUser){
        this.mainUser = mainUser;
        this.maxUser = maxUser;
        this.minUser = minUser;
    }

    public Object getMinUser() {
        return minUser;
    }

    public void setMinUser(Object minUser) {
        this.minUser = minUser;
    }

    public Object getMaxUser() {
        return maxUser;

    }

    public void setMaxUser(Object maxUser) {
        this.maxUser = maxUser;
    }

    public Object getMainUser() {
        return mainUser;
    }

    public void setMainUser(Object mainUser) {
        this.mainUser = mainUser;
    }
}

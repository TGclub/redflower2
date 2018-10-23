package com.test.redflower2.enums;

public enum UserInfoStateEnum {
    INCOMPLETED(0),
    COMPLETED(1);

    private Integer value;

    UserInfoStateEnum(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

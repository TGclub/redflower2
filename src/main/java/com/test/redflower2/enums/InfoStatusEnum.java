package com.test.redflower2.enums;

public enum InfoStatusEnum {
    /**
     * hign代表亲密
     * low代表一般亲密
     * rank为亲密度分水岭，大于rank为high,小于rank为low
     *  亲密：high>rank
     *  一般亲密
     */
    LOW(0),
    HIGH(1),
    rank(10);

    private Integer value;

    InfoStatusEnum(int value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
}

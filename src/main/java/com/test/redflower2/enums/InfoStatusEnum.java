package com.test.redflower2.enums;

public enum InfoStatusEnum {
    /**
     * hign代表较亲密
     * low代表一般亲密
     * rank为亲密度分水岭，大于rank为high,小于rank为low
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

package com.test.redflower2.pojo.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class WxInfo implements Serializable {

    @Getter(AccessLevel.PACKAGE)
    @Setter
    private String code;

    private String encryptedData;

    private String iv;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

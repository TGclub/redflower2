package com.test.redflower2.pojo.dto;

public class WeChatResponseDto {

    private String openid;
    private String session_key;

    public WeChatResponseDto(String opeid, String session_key){
        this.openid = opeid;
        this.session_key = session_key;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getOpenid() {

        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}

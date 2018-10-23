package com.test.redflower2.exception;

public class UserInfoException extends Exception {
    private String message ;
    public UserInfoException(){

    }

    public UserInfoException(String message){
        this.message=message;
    }
}

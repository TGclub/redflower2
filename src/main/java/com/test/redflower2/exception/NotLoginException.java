package com.test.redflower2.exception;

/**
 * <p>Created on 18-10-14</p>
 *
 * @author:StormWangxhu
 * @description: <p>描述</p>
 */
public class NotLoginException extends Exception {
    public NotLoginException() {
    }

    public NotLoginException(String message) {
        super(message);
    }
}

package com.test.redflower2.utils;

public class ObjectUtil {

    public static boolean isEmpty(Object object){
        if (object.equals("")||object==null){
            return true;
        }else {
            return false;
        }
    }
}

package com.test.redflower2.utils;

public class ObjectUtil {

    /**
     * 判断任意对象是否为空
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object){
        return object==null;
    }

    /**
     * 判断字符串是否为空
     * @param string
     * @return
     */
    public static boolean isStringEmpty(String string){
        if (string==null||string.equals("")){
            return true;
        }else {
            return false;
        }
    }
}

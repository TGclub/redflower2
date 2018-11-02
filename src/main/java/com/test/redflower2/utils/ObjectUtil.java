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

    /**
     * 求一个数的绝对值
     * @param num
     * @return
     */
    public static Integer abs(Integer num){
        return num>0?num:-num;
    }
}

package com.sun.czjkxm.utils;

import java.util.Random;

/**
 * 生成验证码大的工具类
 */
public class ValidateCodeUtils {
    /**
     * 生成4位或5位的验证码
     */
    public static Integer generateValidateCode(int length){
        Integer code = null;
        if (length == 4) {
            code = new Random().nextInt(9999);
            if(code<1000){
                code+=1000;
            }
        }else if(length == 6){
            code = new Random().nextInt(999999);
            if(code<100000){
                code+=100000;
            }
        }else{
            //throw new RuntimeException("只能在4和6(code)");
            throw new RuntimeException("只能在4和6位的(code)");
        }
        return code;
    }

    /**
     * 生成指定长度的验证码
     */
    public static String generateValidateCode4String(int length){
        Random random = new Random();
        String hash = Integer.toHexString(random.nextInt());
        String capstr = hash.substring(0, length);
        return capstr;
    }
}

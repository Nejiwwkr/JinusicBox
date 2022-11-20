package com.crystalx.generalduty.conf;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.crystalx.generalduty.conf.Digit32.digits32;

public class Confound {
    public static String confoundCode (Long seed,String key) {
        String tranS,tranS2 = "";
        String res;
        Long tranI;

        //乘三
        tranI = seed * 3;
        //10 -> 16
        tranS = Long.toHexString(tranI);
        //倒序
        for (int i = 1;i <= tranS.length();i++) {
            tranS2 += tranS.substring(tranS.length() - i, tranS.length() - i + 1);
        }
        //加密混淆
        tranS2 += key;
        //16 -> 8
        tranI = Long.parseLong(tranS2,16);
        tranS = Long.toOctalString(tranI);
        //倒序
        tranS2 = "";
        for (int i = 1;i <= tranS.length();i++) {
            tranS2 += tranS.substring(tranS.length() - i, tranS.length() - i + 1);
        }
        //8 -> 16
        tranI = Long.parseLong(tranS2,8);
        tranS2 = Long.toHexString(tranI);
        //加密混淆
        tranS2 += key;
        //16 -> 32
        tranI = Long.parseLong(tranS2,16);
        tranS2 = digits32(tranI);
        //大写
        tranS2 = tranS2.toUpperCase();

        res = tranS2;
        return res;
    }

    public static Long deConfoundCode (String code,String key){
        String tranS = "",tranS2 = "";
        Long res = 0L,tranI;

        //转小写
        tranS = tranS.toLowerCase();
        //32 -> 16
        tranI = Long.parseLong(code,32);
        tranS = Long.toHexString(tranI);
        //反混淆
        tranS = tranS.substring(0,tranS.length() - key.length());
        //16 -> 8
        tranI = Long.parseLong(tranS,16);
        tranS = Long.toOctalString(tranI);
        //倒序
        tranS2 = "";
        for (int i = 1;i <= tranS.length();i++) {
            tranS2 += tranS.substring(tranS.length() - i, tranS.length() - i + 1);
        }
        //8 -> 16
        tranI = Long.parseLong(tranS2,8);
        tranS = Long.toHexString(tranI);
        //反混淆
        tranS = tranS.substring(0,tranS.length() - key.length());
        //倒序
        tranS2 = "";
        for (int i = 1;i <= tranS.length();i++) {
            tranS2 += tranS.substring(tranS.length() - i, tranS.length() - i + 1);
        }
        //16 -> 10
        tranI = Long.parseLong(tranS2,16);
        //除三
        res = tranI / 3;

        return res;
    }

    public static boolean isValidCode (String code,String key) {
        Date date = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yMdHm");
        String s = spf.format(date);
        s = s.substring(3,10) + "0";
        boolean res = false;

        if (deConfoundCode(code,key).equals(Long.valueOf(s))) res = true;
        return res;
    }
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yMdHm");
        String s = spf.format(date);
        s = s.substring(3,10) + "0";

        String key = "e";
        Long seed = Long.valueOf(s);
        System.out.println(confoundCode(seed,key));
        System.out.println(deConfoundCode(confoundCode(seed,key),key));

        System.out.println(isValidCode("3E6RCOGU",key));
        //System.out.println(confoundCode(123456789L,"ee"));
    }
}


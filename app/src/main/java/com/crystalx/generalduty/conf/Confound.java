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
        if (tranS.substring(tranS.length() - key.length()).equalsIgnoreCase(key)) {
            tranS = tranS.substring(0,tranS.length() - key.length());
        }else {
            return 1111111L;
        }
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
        String s = getDate();
        boolean res = false;

        if (deConfoundCode(code,key).equals(Long.valueOf(s))) res = true;
        return res;
    }

    public static String getValidCode (String key) {
        String res = confoundCode(Long.valueOf(getDate()),key);
        return res;
    }

    public static String getDate () {
        Date date = new Date();
        SimpleDateFormat spf_y = new SimpleDateFormat("y");
        SimpleDateFormat spf_mo = new SimpleDateFormat("M");
        SimpleDateFormat spf_d = new SimpleDateFormat("d");
        SimpleDateFormat spf_h = new SimpleDateFormat("h");
        SimpleDateFormat spf_mi = new SimpleDateFormat("m");
        String s_y = spf_y.format(date);
        String s_mo = spf_mo.format(date);
        String s_d = spf_d.format(date);
        String s_h = spf_h.format(date);
        String s_mi = spf_mi.format(date);
        String s = "";
        s += s_y.substring(3);
        if (s_mo.length() == 1) s += 0 + s_mo;
        if (s_mo.length() == 2) s += s_mo;
        if (s_d.length() == 1) s += 0 + s_d;
        if (s_d.length() == 2) s += s_d;
        if (s_h.length() == 1) s += 0 + s_h;
        if (s_h.length() == 2) s += s_h;
        if (s_mi.length() == 1) s += 0;
        if (s_mi.length() == 2) s += s_mi.charAt(0);
        return s;
    }
}


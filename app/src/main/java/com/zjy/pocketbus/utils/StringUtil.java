package com.zjy.pocketbus.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * com.donutcn.memo.utils
 * Created by 73958 on 2017/7/29.
 */

public class StringUtil {

    /**
     * covert String to {@link Date}
     * @param str String like "2017-08-01 17:25:36"
     * @return
     */
    public static Date string2Date(String str){
        String[] res = str.split(" ");
        String[] date = res[0].split("-");
        String[] time = res[1].split(":");
        // the year minus 1900.
        int year = Integer.valueOf(date[0]) - 1900;
        // the month between 0-11.
        int month = Integer.valueOf(date[1]) - 1;
        int day = Integer.valueOf(date[2]);
        int hrs = Integer.valueOf(time[0]);
        int min = Integer.valueOf(time[1]);
        int sec = Integer.valueOf(time[2]);
        return new Date(year, month, day, hrs, min, sec);
    }

    public static String getMD5(String val) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(val.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String getFileNameWithDate(String tag){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return tag + sdf.format(new Date());
    }

    /**
     * BASE64加密
     */
    public static String encryptBASE64(String key) {
        if (key == null || key.length() < 1) {
            return "";
        }
        return new String(Base64.encode(key.getBytes(), Base64.URL_SAFE));
    }

    /**
     * BASE64解密
     */
    public static String decryptBASE64(String key) {
        if (key == null || key.length() < 1) {
            return "";
        }
        return new String(Base64.decode(key, Base64.URL_SAFE));
    }
}

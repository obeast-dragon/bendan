package com.irm.bendan.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils{
    public static void main(String[] args) {
        System.out.println(encode("2222"));
    }

    public static String encode(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]bytes = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < bytes.length; offset++){
                i =bytes[offset];
                if(i < 0)
                    i += 256;
                if(i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            //16位加密
//            return buf.toString().substring(8,24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static String encode2(CharSequence rawPassword) {
//        byte[] secretBytes;
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(rawPassword.toString().getBytes());
//            secretBytes = md.digest();
//        } catch (NoSuchAlgorithmException e) {
//
//            throw new RuntimeException("md5算法不可用");
//        }
//        String md5code = new BigInteger(1, secretBytes).toString(16);
//        if (md5code.length() == 32) return md5code;
//        StringBuilder zero = new StringBuilder();
//        for (int i = 0; i < 32 - md5code.length(); i++) {
//            zero.append('0');
//        }
//        return zero.toString() + md5code;
//    }


}

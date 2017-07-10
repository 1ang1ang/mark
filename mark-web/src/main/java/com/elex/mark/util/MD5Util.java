package com.elex.mark.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密数据类
 * Created by sun on 17/3/5.
 */
public class MD5Util {
    private static final Logger  logger = LoggerFactory.getLogger(MD5Util.class);

    public static String str2MD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            return base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            logger.error("md5 encrype error! NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("md5 encrype error! UnsupportedEncodingException", e);
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(str2MD5("22222222"));
    }
}

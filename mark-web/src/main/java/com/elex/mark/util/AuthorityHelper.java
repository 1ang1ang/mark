package com.elex.mark.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 权限操作工具类
 * Created by sun on 2017/7/10.
 */
public class AuthorityHelper {

    public static final  String DEFAULT_AUTHORITY = "";
    /**
     * 判断是否有权限
     * @param akey  aString中位置的索引值,也就是权限位
     * @param aString  权限字段,比如 11010101011101
     * @return
     */
    public static boolean hasAuthority(int akey,String aString){
        if(aString==null || "".equals(aString)){
            return false;
        }

        if(akey >= aString.length()) {
            return false;
        }

        char value = aString.charAt(akey);
        if(value == '1'){
            return true;
        }

        return false;
    }

    /**
     * 增加权限
     * @param authority
     * @param key
     * @return
     */
    public static String addAuthority(String authority, int key) {
        if(StringUtils.isBlank(authority)) {
            authority = getBlankAuthority(key);
        } else if(key > authority.length()) {
            authority = fillWithBlankAuthority(authority, key);
        }

        return fillAuthority(authority,key);
    }

    /**
     * 获得一个key长度的空权限串
     * @param key
     * @return
     */
    private static String getBlankAuthority(int key) {
        StringBuilder ret = new StringBuilder();
        for(int i=0;i<=key;i++) {
            ret.append("0");
        }

        return ret.toString();
    }

    /**
     * 补齐权限串 authority 到 key 长度
     * @param authority
     * @param key
     * @return
     */
    private static String fillWithBlankAuthority(String authority, int key) {
        StringBuilder ret = new StringBuilder(authority);
        for(int i=authority.length();i<key;i++) {
            ret.append("0");
        }

        return ret.toString();
    }

    /**
     * 填充authority权限串的key位为有权限
     * @param authority
     * @param key
     * @return
     */
    private static String fillAuthority(String authority, int key) {
        StringBuilder afterFillAuth = new StringBuilder(authority);
        afterFillAuth.setCharAt(key, '1');
        return afterFillAuth.toString();
    }
}

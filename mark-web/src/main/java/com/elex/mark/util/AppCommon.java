package com.elex.mark.util;

import java.util.UUID;

/**
 * 项目常用工具类
 * Created by sun on 2017/7/4.
 */
public class AppCommon {

    /**
     * 生成全局唯一的UID
     */
    public static String getGUID() {
        String guid = UUID.randomUUID().toString();
        return guid.substring(0, 8) + guid.substring(9, 13) + guid.substring(14, 18) + guid.substring(19, 23)
                + guid.substring(24);
    }
}

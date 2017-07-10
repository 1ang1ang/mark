package com.elex.mark.enums;

/**
 * Created by sun on 2017/7/9.
 */
public enum  ApiAuthorityType {
    // 包含了枚举的中文名称, 枚举的索引值
    MANAGE_USER("增删改查用户数据", 1),

    REGISTER("注册", 6),
    LOGIN("登录", 7),
    SHOW_USER_INFO("显示自己用户信息", 8),
    ;
    private String name;
    private int index;

    private ApiAuthorityType(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}

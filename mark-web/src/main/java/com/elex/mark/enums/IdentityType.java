package com.elex.mark.enums;

/**
 * 用户身份信息
 * 0：游客 1：学生 2：教师 3：资料填充员 99：超级管理员
 * Created by sun on 2017/7/4.
 */
public enum  IdentityType {
    GUEST(0),
    STUDENT(1),
    TEACHER(2),
    CONTEN_FILLER(3),
    SUPER_MANAGER(99),
    ;

    private int type;

    IdentityType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

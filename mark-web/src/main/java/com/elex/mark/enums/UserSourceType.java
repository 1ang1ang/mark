package com.elex.mark.enums;

/**
 * Created by sun on 2017/7/17.
 */
public enum UserSourceType {
    PHONE(1),
    EMAIL(2),
    ;

    private int type;

    UserSourceType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

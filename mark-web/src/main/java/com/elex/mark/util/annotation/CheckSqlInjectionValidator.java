package com.elex.mark.util.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckSqlInjectionValidator implements ConstraintValidator<CheckSqlInjection, String> {

    public void initialize(CheckSqlInjection constraintAnnotation) {
    }

    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        } else {
            return this.sqlValidate(object);
        }
    }

    // 效验
    protected boolean sqlValidate(String str) {
        // 统一转为小写
        str = str.toLowerCase();
        // 过滤掉的sql关键字，可以手动添加
        String badStr = "'exec |execute |insert |select |delete |update |count |drop |chr |mid |master |truncate |"
                + "char |declare |sitename |net user |xp_cmdshell |like'|create |"
                + "table |from |grant |use |group_concat |column_name |or |"
                + "information_schema.columns|table_schema|union |where |order |by |"
                + "like ";
        String[] badStrs = badStr.split("\\|");

        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                System.err.println("sql注入验证未通过：" + str);
                return false;
            }
        }
        return true;
    }
}
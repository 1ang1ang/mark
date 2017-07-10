package com.elex.mark.bo;

import com.elex.mark.util.annotation.CheckSqlInjection;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by sun on com.elex.mark/5.
 */
@ApiModel(value = "玩家登录请求参数")
public class UserLoginData {
    @NotNull
    @ApiModelProperty(value = "玩家登录邮箱或者手机号")
    private String value;
    @NotNull
    @CheckSqlInjection
    @ApiModelProperty(value = "密码")
    private String password;
    @NotNull
    @ApiModelProperty(value = "验证码")
    private String validateCode;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}

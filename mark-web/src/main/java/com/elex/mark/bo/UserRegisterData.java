package com.elex.mark.bo;

import com.elex.mark.util.annotation.CheckSqlInjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by sun on 2017/7/5.
 */
@ApiModel(value = "玩家注册请求参数")
public class UserRegisterData {
    @NotNull
    @ApiModelProperty(value = "注册类型，1=手机号注册 2=邮箱注册")
    private int registerType;
    @ApiModelProperty(value = "玩家注册手机号")
    private String phone;
    @ApiModelProperty(value = "玩家注册邮箱")
    private String mail;

    @NotNull
    @CheckSqlInjection
    @ApiModelProperty(value = "密码")
    private String password;
    @NotNull
    @ApiModelProperty(value = "昵称")
    private String name;
    @NotNull
    @ApiModelProperty(value = "验证码")
    private String validateCode;

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}

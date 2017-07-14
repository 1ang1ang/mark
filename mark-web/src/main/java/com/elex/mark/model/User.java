package com.elex.mark.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sun on 2017/7/1.
 */
@ApiModel(value = "用户表")
public class User {

    @ApiModelProperty(value = "用户UUID~唯一标识")
    private String uid;

    private String phoneNum;

    private String email;

    @ApiModelProperty(value = "上次登录类型， 1=手机 2=邮箱")
    private Integer lastLoginType;

    private String password;

    private Integer gender;

    private String name;

    private Integer age;

    @ApiModelProperty(value = "用户身份标识 0：游客 1：学生 2：教师 3：资料填充员 99：超级管理员")
    private Integer identity;

    private long registerTime;
    private long lastLoginTime;
    private String authority;
    private boolean forbidden;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getLastLoginType() {
        return lastLoginType;
    }

    public void setLastLoginType(Integer lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean isForbidden() {
        return forbidden;
    }

    public void setForbidden(boolean forbidden) {
        this.forbidden = forbidden;
    }
}
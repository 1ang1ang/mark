package com.elex.mark.service;

import com.elex.mark.bo.UserLoginData;
import com.elex.mark.bo.UserRegisterData;
import com.elex.mark.enums.IdentityType;
import com.elex.mark.enums.UserSourceType;
import com.elex.mark.error.LogicException;
import com.elex.mark.error.ResultCode;
import com.elex.mark.mapper.UserMapper;
import com.elex.mark.model.User;
import com.elex.mark.util.AppCommon;
import com.elex.mark.util.AuthorityHelper;
import com.elex.mark.util.MD5Util;
import com.elex.mark.util.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by sun on 2017/7/1.
 */

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    public User getByUid(String uid){
        User user=userMapper.selectByPrimaryKey(uid);
        //User user=null;
        return user;
    }

    public User getByName(String name){
        User user=userMapper.selectByName(name);
        //User user=null;
        return user;
    }

    public List<User> getAllUser() {
        return userMapper.selectAll();
    }

    public User register(UserRegisterData registerData, HttpSession session) throws LogicException {
        //验证码验证
        String code = (String) session.getAttribute("codeValidate");
        if (registerData.getValidateCode() == null || !registerData.getValidateCode().equalsIgnoreCase(code)){
            throw new LogicException(ResultCode.PARAMS_ERROR, "ValidateCode error!~");
        }

        User user = new User();
        String value;
        int registerType = registerData.getRegisterType();
        if(registerType == UserSourceType.PHONE.getType()) {
            value = registerData.getPhone();
            if(checkPhoneExist(value)) {
                logger.error("register phone exist!");
                throw new LogicException(ResultCode.PARAMS_ERROR, "input phone num exists!~", value);
            }
            user.setPhoneNum(value);
        } else {
            value = registerData.getMail();
            if(checkEmailExist(value)) {
                logger.error("register email exist!");
                throw new LogicException(ResultCode.PARAMS_ERROR, "input email exists!~", value);
            }
            user.setEmail(value);
        }

        // TODO: 2017/7/6 判断身份
        long now = System.currentTimeMillis();
        user.setUid(AppCommon.getGUID());
        user.setIdentity(IdentityType.STUDENT.getType());
        user.setPassword(MD5Util.str2MD5(registerData.getPassword()));
        user.setName(registerData.getName());
        user.setLastLoginTime(now);
        user.setRegisterTime(now);
        user.setAuthority(AuthorityHelper.DEFAULT_AUTHORITY);
        userMapper.insert(user);
        return user;
    }

    public User login(UserLoginData loginData, HttpSession session) throws LogicException {
        //验证码验证
        String code = (String) session.getAttribute("codeValidate");
        if (loginData.getValidateCode() == null || !loginData.getValidateCode().equalsIgnoreCase(code)){
            throw new LogicException(ResultCode.PARAMS_ERROR, "ValidateCode error!~");
        }

        boolean isEmail = false;

        String password  = loginData.getPassword();
        User user;
        String value = loginData.getValue();
        if(RegexUtil.emailCheck(value)) {
            if(checkEmailExist(value)) {
                logger.error("login email exist!");
                throw new LogicException(ResultCode.PARAMS_ERROR, "input email exists!~", value);
            }
            user = userMapper.selectByEmail(value);
            isEmail = true;
        } else if(RegexUtil.phoneCheck(value)) {
            if(checkPhoneExist(value)) {
                logger.error("login phone exist!");
                throw new LogicException(ResultCode.PARAMS_ERROR, "input phone num exists!~", value);
            }
            user = userMapper.selectByPhone(value);
        } else {
            logger.error("login input value wrong!");
            throw new LogicException(ResultCode.PARAMS_ERROR, "input value not phone and not email~", value);
        }

        if(user == null) {
            throw new LogicException(ResultCode.PARAMS_ERROR, "user not exist with value is " + value + " ,and password is " + password, value);
        }

        if(!user.getPassword().equals(password)) {
            throw new LogicException(ResultCode.PARAMS_ERROR, "user not exist with value is " + value + " ,and password is " + password, value);
        }

        long now = System.currentTimeMillis();
        user.setLastLoginTime(now);
        user.setLastLoginType(isEmail ? UserSourceType.EMAIL.getType() : UserSourceType.PHONE.getType());
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    private boolean checkPhoneExist(String phone) {
        return userMapper.selectByPhone(phone) != null;
    }

    private boolean checkEmailExist(String email) {
        return userMapper.selectByEmail(email) != null;
    }
}

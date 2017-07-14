package com.elex.mark.controller;

import com.elex.mark.bo.UserLoginData;
import com.elex.mark.bo.UserRegisterData;
import com.elex.mark.error.LogicException;
import com.elex.mark.model.User;
import com.elex.mark.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by sun on 2017/7/1.
 */
@RestController
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *
     * @return
     */
//    @Authority(authorityTypes = {ApiAuthorityType.SHOW_USER_INFO})
    @ApiOperation(value="Get all users",notes="requires noting")
    @RequestMapping(method=RequestMethod.GET)
    public List<User> getUsers(){
        return userService.getAllUser();
    }

    @ApiOperation(value="Get user with id",notes="requires the id of user")
    @RequestMapping(value="id/{id}",method=RequestMethod.GET)
    public User getUserById(@PathVariable String id){
        return userService.getByUid(id);
    }

    @ApiOperation(value="Get user with name",notes="requires the name of user")
    @RequestMapping(value="name/{name}",method=RequestMethod.GET)
    public User getUserByName(@PathVariable String name){
        return userService.getByName(name);
    }

    @ApiOperation(value = "user register", notes = "send user info to register")
    @RequestMapping(value = "register", method = RequestMethod.PUT)
    public User register(@Validated @RequestBody UserRegisterData registerData, HttpSession session) throws LogicException {
//        int a = 1 / 0;
        return userService.register(registerData, session);
    }
    @ApiOperation(value = "user login", notes = "send user info to login")
    @RequestMapping(value = "login", method = RequestMethod.PUT)
    public User login(@Validated @RequestBody UserLoginData loginData, HttpSession session) throws LogicException {
//        int a = 1 / 0;
        return userService.login(loginData, session);
    }
}

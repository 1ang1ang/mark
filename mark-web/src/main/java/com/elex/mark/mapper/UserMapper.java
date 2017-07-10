package com.elex.mark.mapper;

import com.elex.mark.model.User;

import java.util.List;

/**
 * Created by sun on 2017/7/4.
 */
public interface UserMapper {
    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uid);

    User selectByName(String name);

    User selectByEmail(String email);

    User selectByPhone(String phone);

    List<User> selectAll();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
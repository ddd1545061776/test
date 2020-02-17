package com.ddd.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ddd.pojo.User;
import com.ddd.pojo.UserCustom;
import com.ddd.pojo.UserQueryVo;

public interface UserMapper {
  

public User findUserBynamepwd(UserQueryVo userQueryVo);

}

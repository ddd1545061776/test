package com.ddd.service;

import java.util.List; 

import org.springframework.stereotype.Service;

import com.ddd.pojo.User;
import com.ddd.pojo.UserCustom;
import com.ddd.pojo.UserQueryVo;
@Service
public interface UserService {


	public User findUserBynamepwd(UserQueryVo userQueryVo);
}

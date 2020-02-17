package com.ddd.service;

import java.util.List;  

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ddd.dao.UserMapper;
import com.ddd.pojo.User;

import com.ddd.pojo.UserQueryVo;
@Service("userService")
public class UserServiceimpl implements UserService {
   @Resource
   UserMapper userMapper;

@Override
public User findUserBynamepwd(UserQueryVo userQueryVo) {
	// TODO Auto-generated method stub
	return userMapper.findUserBynamepwd(userQueryVo);
}




	
	
	}



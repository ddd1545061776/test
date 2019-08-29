package com.ddd.pojo;

public class UserQueryVo {
	@Override
	public String toString() {
		return "UserQueryVo [userCustom=" + userCustom + "]";
	}

	//private User user;
/*	private String username;
	private String password;*/
	private UserCustom userCustom;

	public UserCustom getUserCustom() {
		return userCustom;
	}

	public void setUserCustom(UserCustom userCustom) {
		this.userCustom = userCustom;
	}
 

      
}

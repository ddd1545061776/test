package com.ddd.service.impl;

import com.ddd.dao.AdminDao;
import com.ddd.po.Admin;
import com.ddd.po.PageInfo;
import com.ddd.po.Role;
import com.ddd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户Service接口实现类
 */

@Transactional
@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {
	// 注入UserDao
	@Autowired
	private AdminDao adminDao;
	@Autowired
	PasswordEncoder passwordEncoder;
	//管理登陆查询
//	@Override
//	public Admin findAdmin(Admin admin) {
//		Admin a = adminDao.findAdmin(admin);
//		return a;
//	}




	@Override
	public List<Admin> getAll() {

		List<Admin> adminList = adminDao.getAll();
		return adminList;
	}

	@Override
	public PageInfo<Admin> findPageInfo(String username, Integer a_id, Integer pageIndex, Integer pageSize) {
		PageInfo<Admin> pi = new PageInfo<Admin>();
		pi.setPageIndex(pageIndex);
		pi.setPageSize(pageSize);
		//获取总条数
		Integer totalCount = adminDao.totalCount(username,a_id);
		if (totalCount > 0) {
			pi.setTotalCount(totalCount);
			//每一页显示管理员信息数
			//currentPage = (pageIndex-1)*pageSize  当前页码数减1*最大条数=开始行数
			List<Admin> adminList = adminDao.getAdminList(username, a_id,
					(pi.getPageIndex() - 1) * pi.getPageSize(), pi.getPageSize());
			pi.setList(adminList);
		}
		return pi;
	}

	//添加管理员信息
	@Override
	public int addAdmin(Admin admin) {
		return adminDao.addAdmin(admin);
	}

	//通过id删除管理员信息
	@Override
	public int deleteAdmin(Integer a_id) {
		return adminDao.deleteAdmin(a_id);
	}

	//修改管理员信息
	@Override
	public int updateAdmin(Admin admin) {
		return adminDao.updateAdmin(admin);
	}

	@Override
	public Admin findAdminById(Integer a_id) {
		Admin a = adminDao.findAdminById(a_id);
		return a;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//根据用户名做查询
		Admin admin = adminDao.findAdminByUsername(username);
		List<GrantedAuthority> authorities=null;
		Admin admin2 = adminDao.findAdminAndRoleByUsername(admin.getUsername());
		if (admin2 != null) {
			 authorities = new ArrayList<>();
			List<Role> roles = admin2.getRoles();
			if (roles != null) {
				for (Role role : roles) {
					authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
				}
				admin.setAuthorities(authorities);
			}
		}
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		return admin;
	}


}
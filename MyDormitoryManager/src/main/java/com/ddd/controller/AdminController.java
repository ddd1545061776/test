package com.ddd.controller;


import com.ddd.dao.AdminDao;
import com.ddd.dao.MenuDao;
import com.ddd.po.Admin;
import com.ddd.po.Menu;
import com.ddd.po.PageInfo;
import com.ddd.po.Role;
import com.ddd.service.AdminService;
import com.ddd.service.MenuService;
import com.ddd.util.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户控制器类
 */
@Api(tags = "AdminController")
@Controller
public class AdminController {
	// 依赖注入
	@Autowired
	private AdminService adminService;
	@Autowired
    private MenuService menuService;
	@Autowired
	AdminDao adminDao;
	/**
	 * 用户登录
	 */
	/**
	 * 将提交数据(username,password)写入Admin对象
	 */
	@ApiOperation(value = "用户登录")
	@GetMapping(value = "/dddlogin")
	public String login( Model model, HttpSession session, HttpServletRequest request) {
		// 通过账号和密码查询用户
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Admin user = (Admin) authentication.getPrincipal();
		List<Menu> menus = menuService.findMenusByUsername(user.getUsername());
		model.addAttribute("menus", menus);
			request.getSession().setAttribute("ad", user);
			return "homepage";

	}
	/**
	 * 退出登录
	 */
	@ApiOperation(value = "退出登录")
	@GetMapping(value = "/loginOut")
	public String loginOut(Admin admin, Model model, HttpSession session) {
		session.removeAttribute("ad");
		session.invalidate();
		return "login";

	}

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping(value = "/findAdmin")
	public String findAdmin(String username,Integer pageIndex,
							Integer a_id ,Integer pageSize, Model model) {

		PageInfo<Admin> ai = adminService.findPageInfo(username,
								a_id,pageIndex,pageSize);
		model.addAttribute("ai",ai);
		model.addAttribute("a_username",username);
		return "admin_list";
	}

	/**
	 * 导出Excel
	 */
	@ApiOperation(value = "导出Excel")
	@PostMapping(value = "/exportadminlist" )
    @ResponseBody
	public List<Admin> exportAdmin(){
		List<Admin> admin = adminService.getAll();
		return admin;
	}

	/**
	 * 添加管理员信息
	 */
	@ApiOperation(value = "添加管理员信息")
	@PostMapping(value = "/addAdmin")
	@ResponseBody
	public String addAdmin(@RequestBody Admin admin) {

//		admin.setPassword(MD5Util.MD5EncodeUtf8(admin.getPassword()));
		int a = adminService.addAdmin(admin);
		return "admin_list";
	}

	/**
	 * 删除管理员信息；将请求体a_id写入参数a_id
	 */
	@ApiOperation(value = "删除管理员信息")
	@GetMapping( "/deleteAdmin")
	@ResponseBody
	public String deleteAdmin(Integer a_id) {
		int a = adminService.deleteAdmin(a_id);
		return "admin_list";
	}

	/**
	 * 修改管理员信息
	 */
	/**
	 * 将提交数据(a_id,a_username...)写入Admin对象
	 */
	@ApiOperation(value = "修改管理员信息")
	@PostMapping( value = "/updateAdmin")
	public String updateAdmin(Admin admin) {

		int a = adminService.updateAdmin(admin);
		return "redirect:/findAdmin";
	}


	/**
	 * 根据管理员Id搜索;将请求数据a_id写入参数a_id
	 */
	@ApiOperation(value = "根据管理员Id搜索")
	@GetMapping( "/findAdminById")
	public String findAdminById(Integer a_id,HttpSession session) {
		Admin a= adminService.findAdminById(a_id);
		session.setAttribute("a",a);
		return "admin_edit";
	}
	@GetMapping( "/test")
	public String test(Integer a_id,HttpSession session) {
		return "login";
	}
}

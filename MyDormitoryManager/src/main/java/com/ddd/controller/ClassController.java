package com.ddd.controller;


import com.ddd.po.Class;
import com.ddd.po.PageInfo;
import com.ddd.service.ClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户控制器类
 */
@Api(tags = "ClassController")
@Controller
public class ClassController {
	// 依赖注入
	@Autowired
	private ClassService classService;

	/**
	 * 分页查询
	 * pageIndex 当前页码
	 * pageSize  显示条数
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping(value = "/findClass")
	public String findClass(Integer c_classid, String c_classname, String c_counsellor,
							Integer pageIndex, Integer pageSize, Model model) {

	  PageInfo<Class> ci = classService.findPageInfo(c_classname,c_counsellor,
			  c_classid,pageIndex,pageSize);
	  model.addAttribute("ci",ci);
	  model.addAttribute("c_classid",c_classid);
		model.addAttribute("c_classname",c_classname);
		model.addAttribute("c_counsellor",c_counsellor);
		return "class_list";
	}

	/**
	 * 导出Excel
	 */
	@ApiOperation(value = "导出Excel")
	@PostMapping(value = "/exportclasslist")
	@ResponseBody
	public List<Class> exportClass(){
		List<Class> classList = classService.getAll();
		return classList;
	}

	/**
	 * 删除学生信息
	 */
	@ApiOperation(value = "删除学生信息")
	@GetMapping( "/deleteClass")
	@ResponseBody
	public String deleteClass(Integer c_id) {
		int c = classService.deleteClass(c_id);
		return "class_list";
	}

	/**
	 * 添加班级信息
	 */
	@ApiOperation(value = "添加班级信息")
	@PostMapping(value = "/addClass")
	@ResponseBody
	public String addClass( @RequestBody Class uclass) {
		int c = classService.addClass(uclass);
		return "class_list";
	}

	/**
	 * 通过Id查询班级
	 * @param c_id
	 * @param session
	 * @return
	 */
	@ApiOperation(value = "通过Id查询班级")
	@GetMapping( "/findClassById")
	public String findClassById( Integer c_id,HttpSession session) {
		Class c= classService.findClassById(c_id);
		session.setAttribute("c",c);
		return "class_edit";
	}

	/**
	 * 修改班级信息
	 */
	@ApiOperation(value = "修改班级信息")
	@PostMapping(value = "/updateClass")
	public String updateClass( Class uclass) {
		int c = classService.updateClass(uclass);
		return "redirect:/findClass";
	}

	/**
	 * 班级人员信息查询
	 */
	@ApiOperation(value = "班级人员信息查询")
	@GetMapping(value = "/findClassStudent")
	public String findClassStudent(Class uclass,Model model) {
		List<Class> c = classService.findClassStudent(uclass);
		model.addAttribute("cs",c);
		return "class_Studentlist";
	}

	//采用Ajax来提交表单，并返回JSON数据
//	@RequestMapping(value = "/findClassStudentlist",method = RequestMethod.POST)
//	@ResponseBody
//	public List<Class> findClassStudentlist(@RequestBody Class uclass){
//		List<Class> c = classService.findClassStudent(uclass);
//		return c;
//	}
}

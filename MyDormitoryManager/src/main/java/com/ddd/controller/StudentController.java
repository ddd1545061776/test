package com.ddd.controller;


import com.ddd.po.PageInfo;
import com.ddd.po.Student;
import com.ddd.service.StudentService;
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
@Api(tags = "StudentController")
@Controller
public class StudentController {
	// 依赖注入
	@Autowired
	private StudentService studentService;

	/**
	 * 分页查询
	 * pageIndex 当前页码
	 * pageSize  显示条数
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping(value = "/findStudent")
	public String findStudent(String s_name, Integer s_studentid,Integer s_classid, String s_classname,
							  Integer pageIndex, Integer pageSize, Model model) {

	  PageInfo<Student> pageInfo = studentService.findPageInfo(s_name,s_studentid,s_classid,
			  					s_classname,pageIndex,pageSize);
	  model.addAttribute("pageInfo",pageInfo);
	  model.addAttribute("s_name",s_name);
		model.addAttribute("s_studentid",s_studentid);
		model.addAttribute("s_classid",s_classid);
		model.addAttribute("s_classname",s_classname);
		return "student_list";
	}

	/**
	 * 导出Excel
	 */
	@ApiOperation(value = "导出Excel")
	@PostMapping(value = "/exportstudentlist")
	@ResponseBody
	public List<Student> exportStudent(){
		List<Student> studentList = studentService.getAll();
		return studentList;
	}

	/**
	 * 删除学生信息
	 */
	@ApiOperation(value = "删除学生信息")
	@GetMapping( "/deleteStudent")
	@ResponseBody
	public String deleteStudent(Integer s_id) {
		int s = studentService.deleteStudent(s_id);
		return "student_list";
	}

/**
 * 添加学生信息
 */
@ApiOperation(value = "添加学生信息")
   @PostMapping(value = "/addStudent")
   @ResponseBody
   public String addStudent(@RequestBody Student student) {
	   int s = studentService.addStudent(student);
	    return "student_list";
    }

	/**
	 * 修改学生信息
	 */
	@ApiOperation(value = "修改学生信息")
	@PostMapping(value = "/updateStudent")
	public String updateStudent( Student student) {
		int s = studentService.updateStudent(student);
		return "redirect:/findStudent";
	}

	@ApiOperation(value = "findStudentById")
    @GetMapping( "/findStudentById")
    public String findStudentById(Integer s_id,HttpSession session) {

        Student s= studentService.findStudentById(s_id);
        session.setAttribute("s",s);
        return "student_edit";
    }
}

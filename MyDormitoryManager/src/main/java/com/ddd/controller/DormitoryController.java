package com.ddd.controller;



import com.ddd.service.DormitoryService;
import com.ddd.po.Dormitory;
import com.ddd.po.PageInfo;
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

@Api(tags = "DormitoryController")
@Controller
public class DormitoryController {
	// 依赖注入
	@Autowired
	private DormitoryService dormitoryService;

	/**
	 * 分页查询
	 * pageIndex 当前页码
	 * pageSize  显示条数
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping(value = "/findDormitory")
	public String findDormitory(String a_name,Integer s_dormitoryid,String d_dormbuilding,
								Integer pageIndex, Integer pageSize, Model model) {

	  PageInfo<Dormitory> di = dormitoryService.findPageInfo(a_name,s_dormitoryid,
			  d_dormbuilding,pageIndex,pageSize);
	  model.addAttribute("di",di);
		model.addAttribute("a_name",a_name);
		model.addAttribute("s_dormitoryid",s_dormitoryid);
		model.addAttribute("d_dormbuilding",d_dormbuilding);

		return "dormitory_list";
	}

	/**
	 * 导出Excel
	 */
	@ApiOperation(value = "导出Excel")
	@PostMapping(value = "/exportdormitorylist")
	@ResponseBody
	public List<Dormitory> exportDormitory(){
		List<Dormitory> dormitoryList = dormitoryService.getAll();
		return dormitoryList;
	}

	/**
	 * 添加宿舍信息
	 */
	@ApiOperation(value = "添加宿舍信息")
	@PostMapping(value = "/addDormitory")
	@ResponseBody
	public String addDormitory( @RequestBody Dormitory dormitory) {
		int d = dormitoryService.addDormitory(dormitory);
		return "dormitory_list";
	}

	/**
	 * 删除宿舍信息
	 */
	@ApiOperation(value = "删除宿舍信息")
	@GetMapping( "/deleteDormitory")
	@ResponseBody
	public String deleteDormitory(Integer d_id) {
		int d = dormitoryService.deleteDormitory(d_id);
		return "dormitory_list";
	}

	/**
	 * 修改学生信息
	 */
	@ApiOperation(value = "修改学生信息")
	@PostMapping( "/updateDormitory")
	public String updateDormitory( Dormitory dormitory) {
		int d = dormitoryService.updateDormitory(dormitory);
		return "redirect:/findDormitory";
	}

	@ApiOperation(value = "findDormitoryById")
	@GetMapping( "/findDormitoryById")
	public String findDormitoryById(Integer d_id,HttpSession session) {

		Dormitory d= dormitoryService.findDormitoryById(d_id);
		session.setAttribute("d",d);
		return "dormitory_edit";
	}

	/**
	 * 宿舍人员信息查询
	 */
	@ApiOperation(value = "宿舍人员信息查询")
	@GetMapping(value = "/findDormitoryStudent")
	public String findDormitoryStudent(Dormitory dormitory,Model model) {
		List<Dormitory> d = dormitoryService.findDormitoryStudent(dormitory);
		model.addAttribute("ds",d);
		model.addAttribute("s_dormitoryid",dormitory.getS_dormitoryid());
		return "dormitory_Studentlist";
	}


}

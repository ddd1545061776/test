package com.ddd.controller;

import com.ddd.po.PageInfo;
import com.ddd.po.Visitor;
import com.ddd.service.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: dormitorySystem
 * @description: 访客管理
 * @author: Joyrocky
 * @create: 2019-05-14 12:37
 **/
@Api(tags = "VisitorController")
@Controller
public class VisitorController {
    //依赖注入
    @Autowired
    private VisitorService visitorService;
    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/findVisitor")
    public String findVisitor(String v_name, Integer v_phone , Integer pageIndex,
                              Integer pageSize, Model model) {

        PageInfo<Visitor> pi = visitorService.findPageInfo(v_name,v_phone,pageIndex,pageSize);
        model.addAttribute("pi",pi);
        model.addAttribute("v_name",v_name);
        model.addAttribute("v_phone",v_phone);

        return "visitor_list";
    }

    /**
     * 导出Excel
     */
    @ApiOperation(value = "导出Excel")
    @PostMapping(value = "/exportvisitorlist")
    @ResponseBody
    public List<Visitor> exportVisitor(){
        List<Visitor> visitorList = visitorService.getAll();
        return visitorList;
    }

    /**
     * 添加学生信息
     */
    @ApiOperation(value = "添加学生信息")
    @PostMapping(value = "/addVisitor")
    @ResponseBody
    public String addStudent(@RequestBody Visitor visitor) {
        int v = visitorService.addVisitor(visitor);
        return "visitor_list";
    }

}


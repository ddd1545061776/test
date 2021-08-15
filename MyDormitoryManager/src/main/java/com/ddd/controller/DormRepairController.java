package com.ddd.controller;

import com.ddd.po.DormRepair;
import com.ddd.po.PageInfo;
import com.ddd.service.DormRepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: dormitorySystem
 * @description: 维修登记
 * @author: Joyrocky
 * @create: 2019-04-27 17:13
 **/
@Api(tags = "DormRepairController")
@Controller
public class DormRepairController {
    // 依赖注入
    @Autowired
    private DormRepairService dormRepairService;

    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/findDormRepair")
    public String findDormRepair(Integer d_id,String d_dormbuilding,
                                 Integer pageIndex, Integer pageSize, Model model) {

        PageInfo<DormRepair> di = dormRepairService.findPageInfo(d_id,d_dormbuilding,
                                                    pageIndex,pageSize);
        model.addAttribute("di",di);
        model.addAttribute("d_id",d_id);
        model.addAttribute("d_dormbuilding",d_dormbuilding);
        return "dormrepair_list";
    }

    /**
     * 导出Excel
     */
    @ApiOperation(value = "导出Excel")
    @PostMapping(value = "/exportdormrepairlist")
    @ResponseBody
    public List<DormRepair> exportDormrepair(){
        List<DormRepair> dormRepairList = dormRepairService.getAll();
        return dormRepairList;
    }


    /**
     * 添加宿舍信息
     */
    @ApiOperation(value = "添加宿舍信息")
    @PostMapping(value = "/addDormRepair")
    @ResponseBody
    public String addDormitory( @RequestBody DormRepair dormrepair) {
        int d = dormRepairService.addDormRepair(dormrepair);
        return "dormrepair_list";
    }

    /**
     * 删除宿舍信息
     */
    @ApiOperation(value = "删除宿舍信息")
    @GetMapping( "/deleteDormRepair")
    @ResponseBody
    public String deleteDormRepair(Integer r_id) {
        int d = dormRepairService.deleteDormRepair(r_id);
        return "dormrepair_list";
    }

    /**
     * 修改学生信息
     */
    @ApiOperation(value = "修改学生信息")
    @PostMapping( "/updateDormRepair")
    public String updateDormRepair( DormRepair dormrepair) {
        int d = dormRepairService.updateDormRepair(dormrepair);
        return "redirect:/findDormRepair";
    }

    @ApiOperation(value = "findDormRepairById")
    @GetMapping( "/findDormRepairById")
    public String findDormRepairById(Integer r_id, HttpSession session) {

        DormRepair d= dormRepairService.findDormRepairById(r_id);
        session.setAttribute("d",d);
        return "dormrepair_edit";
    }

}


package com.ddd.controller;

import com.ddd.po.DormClean;
import com.ddd.po.PageInfo;
import com.ddd.service.DormCleanService;
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
 * @description: 宿舍卫生控制器
 * @author: Joyrocky
 * @create: 2019-04-24 11:19
 **/
@Api(tags = "DormCleanController")
@Controller
public class DormCleanController {

    //依赖注入
    @Autowired
    private DormCleanService dormCleanService;

    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/findDormClean")
    public String findDormClean(Integer d_id,String d_dormbuilding,
                                Integer pageIndex, Integer pageSize, Model model) {

        PageInfo<DormClean> di = dormCleanService.findPageInfo(d_id,d_dormbuilding,
                                            pageIndex,pageSize);
        model.addAttribute("di",di);
        model.addAttribute("d_id",d_id);
        model.addAttribute("d_dormbuilding",d_dormbuilding);

        return "dormclean_list";
    }

    /**
     * 导出Excel
     */
    @ApiOperation(value = "导出Excel")
    @PostMapping(value = "/exportdormcleanlist")
    @ResponseBody
    public List<DormClean> exportDormclean(){
        List<DormClean> dormclean = dormCleanService.getAll();
        return dormclean;
    }

    /**
     * 添加宿舍卫生信息
     */
    @ApiOperation(value = "添加宿舍卫生信息")
    @PostMapping(value = "/addDormClean" )
    @ResponseBody
    public String addDormClean( @RequestBody DormClean dormclean) {
        int d = dormCleanService.addDormClean(dormclean);
        return "dormclean_list";
    }

    /**
     * 删除宿舍卫生信息
     */
    @ApiOperation(value = "删除宿舍卫生信息")
    @GetMapping( "/deleteDormClean")
    @ResponseBody
    public String deleteDormClean(Integer g_id) {
        int d = dormCleanService.deleteDormClean(g_id);
        return "dormclean_list";
    }

    /**
     * 修改宿舍卫生信息
     */
    @ApiOperation(value = "修改宿舍卫生信息")
    @PostMapping( "/updateDormClean")
    public String updateDormClean( DormClean dormclean) {
        int d = dormCleanService.updateDormClean(dormclean);
        return "redirect:/findDormClean";
    }

    /**
     * 通过id查询DormClean
     * @param g_id
     * @param session
     * @return
     */
    @ApiOperation(value = "通过id查询DormClean")
    @GetMapping( "/findDormCleanById")
    public String findDormCleanById(Integer g_id, HttpSession session) {

        DormClean d= dormCleanService.findDormCleanById(g_id);
        session.setAttribute("d",d);
        return "dormclean_edit";
    }


}


package com.ddd.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.websocket.server.PathParam;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 邓冬冬
 * @date 2021/7/7
 */
@Controller
public class UpdateController {
    //资源上传
    @RequestMapping(value="upload",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody //json交互注解
    public Map<String, Object> uploadResource(HttpServletRequest request, HttpServletResponse response, HttpSession session, MultipartFile file) throws Exception{
        //UserExtend teacher = (UserExtend) session.getAttribute("userInfo");
        //String path = session.getServletContext().getRealPath("\\resource\\"+teacher.getUserCode());//这样只会保存到项目的文件目录下
        String path = "E://nantong//demo01//";
        String fileName = file.getOriginalFilename();
        File dir = new File(path,fileName);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        file.transferTo(dir);
        //upload要求返回的数据格式
        Map<String, Object> uploadData = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        //JSONArray data = JSONArray.fromObject(dir.getPath());
        uploadData.put("code", "0");
        uploadData.put("msg", "");
        //将文件路径返回
        data.put("src", dir.getPath());
        uploadData.put("data", data);
        //uploadData.put("data", "{\'src\':\'"+dir.getPath()+"\'}");
        return uploadData;
        /* 返回接口格式
	          "code": 0
	          ,"msg": ""
	          ,"data": {
	           "src": "http://cdn.layui.com/123.jpg"
	          }
	    */
    }
    @GetMapping(value ="/imageShow")
    public String imageShow(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getParameter("path");
        // 取路径
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(path);
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "homepage";
    }

    @GetMapping(value ="/imageDown")
    public String imageDown(String path,HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(request.getSession().getServletContext().getMimeType(path));
           if (null==path||"".equals(path)) {
               throw new RuntimeException("不能为空");
           }

        int i = path.lastIndexOf("\\");
        String substring = path.substring(i+1);
        response.setHeader("Content-Disposition","attachment;filename="+substring);
        // 取路径
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(path);
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "homepage";
    }
}

package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.ProjectSubitem;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ProjectMapper;
import com.huag.collaboration.mapper.ProjectSubitemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/8 15:34
 */
@Controller
public class ProjectSubitemController {

    @Autowired
    ProjectSubitemMapper projectSubitemMapper;

    @Autowired
    ProjectMapper projectMapper;


    @DeleteMapping("/project/projectSubitem/{id}")
    public String deleteProjectSubitem(@PathVariable("id") String id, Model model){
        ProjectSubitem projectSubitem = projectSubitemMapper.findById(id);

        System.out.println("id: " + id);

        String projectId = projectSubitem.getProjectId();

        System.out.println("projectId: " + projectId);

        Project project = projectMapper.findById(projectId);
        model.addAttribute("project", project);
        List<ProjectSubitem> projectSubitemList = projectSubitemMapper.findByProjectId(projectId);
        model.addAttribute("projectSubitemList", projectSubitemList);

        projectSubitemMapper.deleteById(id);
        return "redirect:/project/add";
//        return "project/add";
    }

    @ResponseBody
    @RequestMapping(value = "/project/projectSubitem/edit")
    public BaseResponse<ProjectSubitem> toEditPage(HttpServletRequest request){
        BaseResponse<ProjectSubitem> result = new BaseResponse<ProjectSubitem>();
        String id = String.valueOf(request.getParameter("id"));
        ProjectSubitem projectSubitem = projectSubitemMapper.findById(id);
        System.out.println(projectSubitem);
        result.code = 200;
        result.setData(projectSubitem);
        return result;
    }

    @PostMapping("/projectSubitem")
    public String addEmp(ProjectSubitem projectSubitem){
        System.out.println(projectSubitem);
        projectSubitemMapper.insert(projectSubitem);
        return "redirect:/project/add";
    }

    /**
     * ajax请求findByProjectId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/project/projectSubitem/findByProjectId")
    public BaseResponse<List<ProjectSubitem>> findByProjectId(HttpServletRequest request){
        BaseResponse<List<ProjectSubitem>> result = new BaseResponse<>();
        String projectId = String.valueOf(request.getParameter("projectId"));
        List<ProjectSubitem> projectSubitemList = projectSubitemMapper.findByProjectId(projectId);
        System.out.println(projectSubitemList);
        result.code = 200;
        result.setData(projectSubitemList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/project/projectSubitem/delete")
    public BaseResponse<List<ProjectSubitem>> delete(HttpServletRequest request){
        BaseResponse<List<ProjectSubitem>> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        projectSubitemMapper.deleteById(id);
        result.code = 200;
        return result;
    }



}

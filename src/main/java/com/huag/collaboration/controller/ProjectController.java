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
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * @author huag
 * @date 2019/9/26 17:18
 */
@Controller
public class ProjectController {


    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectSubitemMapper projectSubitemMapper;

    /**
     * 页面跳转
     * @param model
     * @return
     */
    @GetMapping("/projects")
    public String  list(Model model){
        return "project/list";
    }

    /**
     * 跳转到添加页面
     * @param model
     * @return
     */
    @GetMapping("/project")
    public String toAddPage(Model model){
        return "project/add";
    }

    /**
     * 提交添加表单
     * @param project
     * @return
     */
    @PostMapping("/project")
    public String addProject(Project project){
        projectMapper.insert(project);
        return "redirect:/projects";
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/project/{id}")
    public String toEditProject(@PathVariable("id") Integer id, Model model){
        Project project = projectMapper.findById(id);
        model.addAttribute("project", project);
        List<ProjectSubitem> projectSubitemList = projectSubitemMapper.findByProjectId(id);
        model.addAttribute("projectSubitemList", projectSubitemList);
        return "project/add";
    }

    @DeleteMapping("project/{id}")
    public String deleteProject(@PathVariable("id") Integer id){
        projectMapper.deleteById(id);
        return "redirect:/projects";
    }

    /**
     * ajax请求，查询所有数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/project/findAll")
    public BaseResponse<List<Project>> findAll(HttpServletRequest request){
        BaseResponse<List<Project>> result = new BaseResponse<>();
//        List<Project> projects = projectMapper.findAllIn(new String[]{"马东铁路", "长江大桥"});
        List<Project> projects = projectMapper.findAll();
        projects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                long timeLength = project.getStopTime().getTime() - project.getStartTime().getTime();
                project.setLeftTime(timeLength/1000/60/60/24 + "");
            }
        });
        System.out.println(projects);
        result.code = 200;
        result.setData(projects);
        return result;
    }

    /**
     * ajax请求
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/project/findByProjectName")
    public BaseResponse<List<Project>> findByProjectName(HttpServletRequest request) throws Exception{
        BaseResponse<List<Project>> result = new BaseResponse<>();
        String projectName = String.valueOf(request.getParameter("projectName"));
        projectName = URLDecoder.decode(projectName, "UTF-8");
        List<Project> projects = projectMapper.findByProjectName(projectName);
        projects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                long timeLength = project.getStopTime().getTime() - project.getStartTime().getTime();
                project.setLeftTime(timeLength/1000/60/60/24 + "");
            }
        });
        System.out.println(projects);
        result.code = 200;
        result.setData(projects);
        return result;
    }


}

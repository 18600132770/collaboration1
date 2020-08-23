package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.ProjectSubitem;
import com.huag.collaboration.entities.ProjectSummary;
import com.huag.collaboration.entities.User;
import com.huag.collaboration.entities.base.PageBaseResponse;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ProjectMapper;
import com.huag.collaboration.mapper.ProjectSubitemMapper;
import com.huag.collaboration.mapper.ProjectSummaryMapper;
import com.huag.collaboration.utils.DateUtils;
import com.huag.collaboration.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    ProjectSummaryMapper projectSummaryMapper;

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
//    @ModelAttribute("model")
    @GetMapping("/project/{id}")
    public String toEditProject(@PathVariable("id") Integer id, Model model){
//        Project project = projectMapper.findById(id);
//        model.addAttribute("project", project);
//        List<ProjectSubitem> projectSubitemList = projectSubitemMapper.findByProjectId(id);
//        System.out.println(projectSubitemList);
//        model.addAttribute("projectSubitemList", projectSubitemList);
        model.addAttribute("projectId", id);

        return "project/add";
    }

    @DeleteMapping("project/{id}")
    public String deleteProject(@PathVariable("id") String id){
        projectMapper.deleteById(Integer.valueOf(id));
        return "redirect:/projects";
    }

    /**
     * ajax请求findByProjectId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/project/findByProjectId")
    public BaseResponse<Project> findByProjectId(HttpServletRequest request){
        BaseResponse<Project> result = new BaseResponse<>();
        String projectId = String.valueOf(request.getParameter("projectId"));
        if(StringUtils.isNotBlank(projectId)){
            Project project = projectMapper.findById(Integer.valueOf(projectId));
            result.setData(project);
        }
        result.code = 200;
        return result;
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
        List<Project> projects = projectMapper.findAll();
        projects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
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
        List<Project> projects = new ArrayList<>();
        if(StringUtils.isNotBlank(projectName)){
            projects = projectMapper.findByProjectName(projectName);
        }else{
            projects = projectMapper.findAll();
        }
        projects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
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
    @RequestMapping(value = "/project/findByProjectNameAndDeptId")
    public BaseResponse<Object> findByProjectNameAndDeptId(HttpServletRequest request) throws Exception{
        BaseResponse<Object> result = new BaseResponse<>();
        String projectName = String.valueOf(request.getParameter("projectName"));
        projectName = URLDecoder.decode(projectName, "UTF-8");
        String departmentId = String.valueOf(request.getParameter("departmentId"));

        int currentPage = RequestUtils.getCurrentPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);

        List<Project> projects = new ArrayList<>();
        if(StringUtils.isNotBlank(projectName)){
            projects = projectMapper.findByProjectNameAndDeptId(projectName, Integer.valueOf(departmentId));
        }else{
            projects = projectMapper.findProjectByDepartmentId(Integer.valueOf(departmentId));
        }

        projects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
        });

        PageInfo<Project> pageInfo = new PageInfo<>(projects, pageSize);

        int pages = pageInfo.getPages();
        long total = pageInfo.getTotal();
        PageBaseResponse response = new PageBaseResponse(projects, currentPage,
                pageSize, pages, total);


        System.out.println(projects);
        result.code = 200;
        result.setData(JSON.parseObject(JSON.toJSONString(response)));
        return result;
    }

    /**
     * 修改或保存
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/project/save")
    public BaseResponse<Project> save(HttpServletRequest request) throws Exception{
        BaseResponse<Project> result = new BaseResponse<>();
        String projectStr = URLDecoder.decode(String.valueOf(request.getParameter("project")), "UTF-8");
        Project project = JSONObject.toJavaObject(JSON.parseObject(projectStr), Project.class);
        if(project.getId() == null || "".equals(project.getId())){
            projectMapper.insert(project);
        }else{
            projectMapper.update(project);
        }
        result.code = 200;
        result.setData(project);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/project/delete")
    public BaseResponse<List<Project>> delete(HttpServletRequest request) throws Exception{
        BaseResponse<List<Project>> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        projectMapper.deleteById(Integer.valueOf(id));
        result.code = 200;
        return result;
    }

    /**
     * ajax请求，findProjectByDepartmentId
     * @param request
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/project/findProjectByDepartmentId")
//    public BaseResponse<Object> findProjectByDepartmentId(HttpServletRequest request){
//        BaseResponse<Object> result = new BaseResponse<>();
//        String departmentId = String.valueOf(request.getParameter("departmentId"));
//        int currentPage = RequestUtils.getCurrentPage(request);
//        int pageSize = RequestUtils.getPageSize(request);
//        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
//        List<Project> projects = projectMapper.findProjectByDepartmentId(Integer.valueOf(departmentId));
//        projects.forEach(project ->{
//            if(project.getStopTime() != null && project.getStartTime() != null){
//                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
//                project.setLeftTime(dateDifferenceByDay + "");
//            }
//        });
//        PageInfo<Project> pageInfo = new PageInfo<>(projects, pageSize);
//
//
//        int pages = pageInfo.getPages();
//        long total = pageInfo.getTotal();
//        PageBaseResponse response = new PageBaseResponse(projects, currentPage,
//                pageSize, pages, total);
//        result.code = 200;
//        result.setData(response);
//        return result;
//    }

    @ResponseBody
    @RequestMapping(value = "/project/findProjectByDepartmentId")
    public BaseResponse<Object> findProjectByDepartmentId(HttpServletRequest request){
        Object user = request.getSession().getAttribute("loginUser");
        BaseResponse<Object> result = new BaseResponse<>();
        String departmentId = String.valueOf(request.getParameter("departmentId"));
        int currentPage = RequestUtils.getCurrentPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);

        List<Project> projects = projectMapper.findByProjectUserName(user.toString());

        String username = user.toString();
        if("admin".equals(username)){
            projects = projectMapper.findByAdmin();
        }else{
            projects = projectMapper.findByProjectUserName(user.toString());
        }


        //List<Project> projects = projectMapper.findProjectByDepartmentId(Integer.valueOf(departmentId));
        projects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
        });
        PageInfo<Project> pageInfo = new PageInfo<>(projects, pageSize);


        int pages = pageInfo.getPages();
        long total = pageInfo.getTotal();
        PageBaseResponse response = new PageBaseResponse(projects, currentPage,
                pageSize, pages, total);
        result.code = 200;
        result.setData(response);
        return result;
    }


    /**
     * ajax请求，更新负责人
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/project/updatePrincipal")
    public BaseResponse<List<Project>> updatePrincipal(HttpServletRequest request) throws Exception{
        BaseResponse<List<Project>> result = new BaseResponse<>();
        String projectId = String.valueOf(request.getParameter("projectId"));
        String project_principalUserId = String.valueOf(request.getParameter("project_principalUserId"));
        String currentDepartmentId = String.valueOf(request.getParameter("currentDepartmentId"));
        Project projectOld = projectMapper.findById(Integer.valueOf(projectId));
        projectOld.setPrincipalId(Integer.valueOf(project_principalUserId));
        int update = projectMapper.update(projectOld);

        String projectName = String.valueOf(request.getParameter("projectName"));
        projectName = URLDecoder.decode(projectName, "UTF-8");



        List<Project> projects = new ArrayList<>();
        if(StringUtils.isNotBlank(projectName)){
            projects = projectMapper.findByProjectNameAndDeptId(projectName, Integer.valueOf(currentDepartmentId));
        }else{
            projects = projectMapper.findProjectByDepartmentId(Integer.valueOf(currentDepartmentId));
        }

        projects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
        });
        System.out.println(projects);
        result.code = 200;
        result.setData(projects);
        return result;
    }

    /**
     * ajax请求，更新总工
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/project/updateChiefEngineer")
    public BaseResponse<List<Project>> updateChiefEngineer(HttpServletRequest request) throws Exception{
        BaseResponse<List<Project>> result = new BaseResponse<>();
        String projectId = String.valueOf(request.getParameter("projectId"));
        String chiefEngineerNameModalInput = String.valueOf(request.getParameter("chiefEngineerNameModalInput"));
        String currentDepartmentId = String.valueOf(request.getParameter("currentDepartmentId"));
        Project projectOld = projectMapper.findById(Integer.valueOf(projectId));
        projectOld.setChiefEngineerId(Integer.valueOf(chiefEngineerNameModalInput));
        int update = projectMapper.update(projectOld);

        String projectName = String.valueOf(request.getParameter("projectName"));
        projectName = URLDecoder.decode(projectName, "UTF-8");

        List<Project> projects = new ArrayList<>();
        if(StringUtils.isNotBlank(projectName)){
            projects = projectMapper.findByProjectNameAndDeptId(projectName, Integer.valueOf(currentDepartmentId));
        }else{
            projects = projectMapper.findProjectByDepartmentId(Integer.valueOf(currentDepartmentId));
        }

        projects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
        });
        System.out.println(projects);
        result.code = 200;
        result.setData(projects);
        return result;
    }


    /**
     * ajax请求，findProjectsByProjectSummaryId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/project/findProjectsByProjectSummaryId")
    public BaseResponse<Object> findProjectsByProjectSummaryId(HttpServletRequest request){
        BaseResponse<Object> result = new BaseResponse<>();
        String projectSummaryId = String.valueOf(request.getParameter("projectSummaryId"));
        int currentPage = RequestUtils.getCurrentPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<Project> projects = projectMapper.findProjectsByProjectSummaryId(Integer.valueOf(projectSummaryId));
        projects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
        });
        PageInfo<Project> pageInfo = new PageInfo<>(projects, pageSize);

        int pages = pageInfo.getPages();
        long total = pageInfo.getTotal();
        PageBaseResponse response = new PageBaseResponse(projects, currentPage,
                pageSize, pages, total);
        result.code = 200;
        result.setData(response);
        return result;
    }

    /**
     * 修改紧急程度
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/project/editEmergencyLevel")
    public BaseResponse<List<Project>> editEmergencyLevel(HttpServletRequest request) throws Exception{
        BaseResponse<List<Project>> result = new BaseResponse<>();
        String projectId = String.valueOf(request.getParameter("projectId"));
        Project project = projectMapper.findById(Integer.valueOf(projectId));
        String emergencyLevel = String.valueOf(request.getParameter("emergencyLevel"));
        emergencyLevel = URLDecoder.decode(emergencyLevel, "UTF-8");
        project.setEmergencyLevel(emergencyLevel);
        projectMapper.update(project);
        result.code = 200;
        result.setData(null);
        return result;
    }


}

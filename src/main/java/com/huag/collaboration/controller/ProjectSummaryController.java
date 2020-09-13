package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.ProjectEnum;
import com.huag.collaboration.entities.ProjectSummary;
import com.huag.collaboration.entities.base.PageBaseResponse;
import com.huag.collaboration.entities.fileTree.FileTree;
import com.huag.collaboration.entities.fileTree.ProjectSummaryFileTree;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.*;
import com.huag.collaboration.utils.DateUtils;
import com.huag.collaboration.utils.JSONUtils;
import com.huag.collaboration.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/22 9:59
 */
@Controller
public class ProjectSummaryController {

    @Autowired
    ProjectSummaryMapper projectSummaryMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    TaskAssignmentMapper taskAssignmentMapper;

    @Autowired
    FileTreeMapper fileTreeMapper;

    @Autowired
    ProjectSummaryFileTreeMapper projectSummaryFileTreeMapper;

    /**
     * ajax请求，查询所有数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/projectSummary/findAll")
    public BaseResponse<List<ProjectEnum>> findAll(HttpServletRequest request){
        BaseResponse<List<ProjectEnum>> result = new BaseResponse<>();

        List<ProjectSummary> projectSummaryList = projectSummaryMapper.findAll();
        projectSummaryList.forEach(projectSummary ->{
            if(projectSummary.getStopTime() != null && projectSummary.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(projectSummary.getStopTime(), projectSummary.getStartTime());
                projectSummary.setLeftTime(dateDifferenceByDay + "");
            }
        });
        List<ProjectEnum> list = new ArrayList<ProjectEnum>();
        projectSummaryList.forEach(projectSummary -> {
            ProjectEnum projectEnum = new ProjectEnum();
            projectEnum.setId(projectSummary.getId());
            projectEnum.setProjectNum(projectSummary.getProjectNum());
            projectEnum.setProjectName(projectSummary.getProjectName());
            projectEnum.setDesignPhase(projectSummary.getDesignPhase());
            projectEnum.setPrincipalId(projectSummary.getPrincipalId());
            projectEnum.setPrincipal(projectSummary.getPrincipal());
            projectEnum.setChiefEngineerId(projectSummary.getChiefEngineerId());
            projectEnum.setChiefEngineer(projectSummary.getChiefEngineer());
            projectEnum.setStartTime(projectSummary.getStartTime());
            projectEnum.setStopTime(projectSummary.getStopTime());
            projectEnum.setLeftTime(projectSummary.getLeftTime());
            projectEnum.setCreateTime(projectSummary.getCreateTime());
            projectEnum.setUpdateTime(projectSummary.getUpdateTime());
            projectEnum.setDeltag(projectSummary.getDeltag());
            projectEnum.setChildren(projectMapper.findProjectsByProjectSummaryId(projectSummary.getId()));
            list.add(projectEnum);
        });
        System.out.println(list);
        result.code = 200;
        result.setData(list);
        return result;
    }

    /**
     * ajax请求
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/projectSummary/findById")
    public BaseResponse<ProjectSummary> findById(HttpServletRequest request){
        BaseResponse<ProjectSummary> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        ProjectSummary projectSummary = projectSummaryMapper.findById(Integer.valueOf(id));
        if(projectSummary.getStopTime() != null && projectSummary.getStartTime() != null){
            Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(projectSummary.getStopTime(), projectSummary.getStartTime());
            projectSummary.setLeftTime(dateDifferenceByDay + "");
        }
        System.out.println(projectSummary);
        result.code = 200;
        result.setData(projectSummary);
        return result;
    }


    /**
     * ajax请求
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/projectSummary/addProject")
    public BaseResponse<ProjectSummary> addProject(HttpServletRequest request) throws Exception{
        BaseResponse<ProjectSummary> result = new BaseResponse<>();
        String[] selectDepartments = request.getParameterValues("selectDepartments");
        String projectStr = URLDecoder.decode(String.valueOf(request.getParameter("projectSummary")), "UTF-8");
        ProjectSummary projectSummary = JSONObject.toJavaObject(JSON.parseObject(projectStr), ProjectSummary.class);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = df2.format(df.parse(projectSummary.getStartTime().replace("Z", " UTC")));
        String stopTime = df2.format(df.parse(projectSummary.getStopTime().replace("Z", " UTC")));
        projectSummary.setStartTime(startTime);
        projectSummary.setStopTime(stopTime);
        projectSummary.setPrincipalId(Integer.valueOf(projectSummary.getPrincipalId()));
        projectSummary.setChiefEngineerId(Integer.valueOf(projectSummary.getChiefEngineerId()));
        System.out.println(projectSummary);

        projectSummaryMapper.insert(projectSummary);//总项目插入数据

        Integer projectSummaryId = projectSummary.getId();//总项目id

        //给部门插入数据
        for (String selectDepartment :
                selectDepartments) {
            Integer departmentId = Integer.valueOf(selectDepartment);
            Project project = new Project();
            project.setProjectSummaryId(projectSummaryId);
            project.setStartTime(projectSummary.getStartTime());
            project.setStopTime(projectSummary.getStopTime());
            project.setDepartmentId(departmentId);
            projectMapper.insert(project);
        }

        //新建项目时把该项目文件树模板加进去
        List<FileTree> fileTreeList = fileTreeMapper.findAll();
        for (FileTree fileTree :
                fileTreeList) {
            System.out.println(fileTree.getId());
            ProjectSummaryFileTree projectSummaryFileTree = new ProjectSummaryFileTree();
            projectSummaryFileTree.setProjectSummaryId(projectSummaryId);
            projectSummaryFileTree.setFileTreeId(fileTree.getId());
            projectSummaryFileTreeMapper.insert(projectSummaryFileTree);
        }

        result.code = 200;
        result.setData(projectSummary);
        return result;
    }

    /**
     * 修改设计阶段
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/projectSummary/editDesignPhase")
    public BaseResponse<List<Project>> editDesignPhase(HttpServletRequest request) throws Exception{
        BaseResponse<List<Project>> result = new BaseResponse<>();
        String projectId = String.valueOf(request.getParameter("projectId"));
        Project project = projectMapper.findById(Integer.valueOf(projectId));
        String designPhase = String.valueOf(request.getParameter("degignPhaseModalInput"));
        designPhase = URLDecoder.decode(designPhase, "UTF-8");
        updateProjectSummaryDesginPhase(Integer.valueOf(projectId), designPhase);
        ProjectSummary projectSummary = projectSummaryMapper.findById(Integer.valueOf(project.getProjectSummaryId()));
        projectSummary.setDesignPhase(designPhase);
        projectSummaryMapper.update(projectSummary);
        result.code = 200;
        result.setData(null);
        return result;
    }


    /**
     * 更新分项目设计阶段
     * @param projectId
     * @param designPhase
     */
    public void updateProjectSummaryDesginPhase(int projectId, String designPhase){
        Project project = projectMapper.findById(Integer.valueOf(projectId));
        if("投标阶段".equals(designPhase)){
            project.setCurrentProcess(6);
        }else if("前期方案研究".equals(designPhase)){
            project.setCurrentProcess(12);
        }else if("初步设计".equals(designPhase)){
            project.setCurrentProcess(42);
        }else if("施工图设计".equals(designPhase)){
            project.setCurrentProcess(82);
        }else if("施工配合".equals(designPhase)){
            project.setCurrentProcess(88);
        }else if("结算".equals(designPhase)){
            project.setCurrentProcess(94);
        }else if("完结".equals(designPhase)){
            project.setCurrentProcess(100);
        }
        projectMapper.update(project);
    }

    /**
     * 项目信息-项目按照用户角色来显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/projectSummary/findByProjectNameAndRoles")
    public BaseResponse<Object> findByProjectNameAndRoles(HttpServletRequest request) throws Exception{
        Object user = request.getSession().getAttribute("loginUser");
        BaseResponse<Object> result = new BaseResponse<>();
        int currentPage = RequestUtils.getCurrentPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);

        String projectName = String.valueOf(request.getParameter("projectName"));
        projectName = URLDecoder.decode(projectName, "UTF-8");
        if (StringUtils.isBlank(projectName) || "null".equals(projectName)){
            projectName = "";
        }

        List<ProjectSummary> projectsSummaryList;

        String username = user.toString();
        if("admin".equals(username)){
            projectsSummaryList = projectSummaryMapper.findByProjectName(projectName);
        }else{
            projectsSummaryList = projectSummaryMapper.findByProjectNameAndRoles(user.toString(), projectName);
        }

        projectsSummaryList.forEach(projectSummary -> {
            if (projectSummary.getStartTime() != null && projectSummary.getStopTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(projectSummary.getStopTime(), projectSummary.getStartTime());
                projectSummary.setLeftTime(dateDifferenceByDay + "");
            }
        });

        System.out.println(projectsSummaryList);

        PageInfo<ProjectSummary> pageInfo = new PageInfo<>(projectsSummaryList, pageSize);

        int pages = pageInfo.getPages();
        long total = pageInfo.getTotal();
        PageBaseResponse response = new PageBaseResponse(projectsSummaryList, currentPage,
                pageSize, pages, total);
        result.code = 200;
        result.setData(response);
        return result;
    }



    @ResponseBody
    @RequestMapping(value = "/projectSummary/update")
    public BaseResponse<ProjectSummary> save(HttpServletRequest request) throws Exception{
        BaseResponse<ProjectSummary> result = new BaseResponse<>();
        String projectStr = URLDecoder.decode(String.valueOf(request.getParameter("projectSummary")), "UTF-8");
        ProjectSummary projectSummary = JSONObject.toJavaObject(JSON.parseObject(projectStr), ProjectSummary.class);
        if(projectSummary.getId() == null || "".equals(projectSummary.getId())){
            projectSummaryMapper.insert(projectSummary);
        }else{
            projectSummaryMapper.update(projectSummary);
        }
        result.code = 200;
        result.setData(projectSummary);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/projectSummary/findByProjectName")
    public BaseResponse<Object> findByProjectName(HttpServletRequest request) throws Exception{
        BaseResponse<Object> result = new BaseResponse<>();
        String projectName = String.valueOf(request.getParameter("projectName"));
        projectName = URLDecoder.decode(projectName, "UTF-8");

        int currentPage = RequestUtils.getCurrentPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);

        List<ProjectSummary> projectSummaryList = new ArrayList<>();
        if(StringUtils.isNotBlank(projectName)){
            projectSummaryList = projectSummaryMapper.findByProjectName(projectName);
        }

        projectSummaryList.forEach(projectSummary -> {
            if (projectSummary.getStartTime() != null && projectSummary.getStopTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(projectSummary.getStopTime(), projectSummary.getStartTime());
                projectSummary.setLeftTime(dateDifferenceByDay + "");
            }
        });


        PageInfo<ProjectSummary> pageInfo = new PageInfo<>(projectSummaryList, pageSize);

        int pages = pageInfo.getPages();
        long total = pageInfo.getTotal();
        PageBaseResponse response = new PageBaseResponse(projectSummaryList, currentPage,
                pageSize, pages, total);
        result.code = 200;
        result.setData(response);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/projectSummary/delete")
    public BaseResponse<ProjectSummary> delete(HttpServletRequest request){
        BaseResponse<ProjectSummary> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        ProjectSummary projectSummary = projectSummaryMapper.findById(Integer.valueOf(id));
        projectSummary.setDeltag("1");
        projectSummaryMapper.update(projectSummary);
        result.code = 200;
        return result;
    }



}

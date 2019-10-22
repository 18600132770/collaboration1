package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.ProjectSummary;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ProjectMapper;
import com.huag.collaboration.mapper.ProjectSummaryMapper;
import com.huag.collaboration.utils.DateUtils;
import com.huag.collaboration.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    /**
     * ajax请求，查询所有数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/projectSummary/findAll")
    public BaseResponse<List<ProjectSummary>> findAll(HttpServletRequest request){
        BaseResponse<List<ProjectSummary>> result = new BaseResponse<>();
        List<ProjectSummary> projectSummaryList = projectSummaryMapper.findAll();
        projectSummaryList.forEach(projectSummary ->{
            if(projectSummary.getStopTime() != null && projectSummary.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(projectSummary.getStopTime(), projectSummary.getStartTime());
                projectSummary.setLeftTime(dateDifferenceByDay + "");
            }
        });
        System.out.println(projectSummaryList);
        result.code = 200;
        result.setData(projectSummaryList);
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

        result.code = 200;
        return result;
    }



}

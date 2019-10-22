package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.ProjectSummary;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ProjectSummaryMapper;
import com.huag.collaboration.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/22 9:59
 */
@Controller
public class ProjectSummaryController {

    @Autowired
    ProjectSummaryMapper projectSummaryMapper;

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



}

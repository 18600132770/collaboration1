package com.huag.collaboration.controller;

import com.huag.collaboration.entities.ProfessionProject;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ProfessionProjectMapper;
import com.huag.collaboration.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/17 15:41
 */
@Controller
public class ProfessionProjectController {

    @Autowired
    ProfessionProjectMapper professionProjectMapper;

    @Autowired
    ProjectMapper projectMapper;

    /**
     * 页面跳转
     * @param model
     * @return
     */
    @GetMapping("/professions")
    public String  list(Model model){
        return "profession/list";
    }


    @ResponseBody
    @RequestMapping(value = "/profession/findByProjectId/")
    public BaseResponse<List<ProfessionProject>> findByProjectId(HttpServletRequest request) throws Exception{
        BaseResponse<List<ProfessionProject>> result = new BaseResponse<>();
        String projectId = String.valueOf(request.getParameter("projectId"));
        projectId = URLDecoder.decode(projectId, "UTF-8");
        List<ProfessionProject> professionProjectList = professionProjectMapper.findByProjectId(Integer.valueOf(projectId));
        System.out.println(professionProjectList);
        result.code = 200;
        result.setData(professionProjectList);
        return result;
    }

    /**
     * 页面跳转
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/profession/{id}")
    public String toEditProject(@PathVariable("id") String id, Model model){
//        Project project = projectMapper.findById(id);
//        model.addAttribute("project", project);
//        List<ProjectSubitem> projectSubitemList = projectSubitemMapper.findByProjectId(id);
//        model.addAttribute("projectSubitemList", projectSubitemList);

        String[] split = id.split(",");
        int projectSummaryId = Integer.valueOf(split[0]);
        int projectId = Integer.valueOf(split[1]);

        List<Project> projectList = projectMapper.findProjectsByProjectSummaryId(projectSummaryId);
        projectList.forEach(project -> {
            if(projectId == project.getId()){
                project.setDeltag("m-a cur");
            }
        });

        model.addAttribute("projectSummaryId", id);
        model.addAttribute("projectList", projectList);

        return "profession/professionImplement";
    }


}

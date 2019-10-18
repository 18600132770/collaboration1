package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.UserProject;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.UserProjectMapper;
import com.huag.collaboration.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/16 22:46
 */
@Controller
public class UserProjectController {

    @Autowired
    UserProjectMapper userProjectMapper;

    /**
     * 页面跳转
     * @param model
     * @return
     */
    @GetMapping("/userProjects")
    public String  list(Model model){
        return "userProject/list";
    }

    /**
     * ajax请求，查询所有数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userProject/findAll")
    public BaseResponse<List<UserProject>> findAll(HttpServletRequest request){
        BaseResponse<List<UserProject>> result = new BaseResponse<>();
        List<UserProject> userProjects = userProjectMapper.findAll();
        userProjects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
        });
        System.out.println(userProjects);
        result.code = 200;
        result.setData(userProjects);
        return result;
    }

    /**
     * ajax请求
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userProject/findByUsername")
    public BaseResponse<List<UserProject>> findByUsername(HttpServletRequest request) throws Exception{
        BaseResponse<List<UserProject>> result = new BaseResponse<>();
        String username = String.valueOf(request.getParameter("username"));
        username = URLDecoder.decode(username, "UTF-8");
        List<UserProject> userProjects = userProjectMapper.findByUsername(username);
        userProjects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
        });
        System.out.println(userProjects);
        result.code = 200;
        result.setData(userProjects);
        return result;
    }


}

package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.ProjectSummary;
import com.huag.collaboration.entities.TaskAssignment;
import com.huag.collaboration.entities.User;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.TaskAssignmentMapper;
import com.huag.collaboration.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/25 11:35
 */
@Controller
public class TaskAssignmentController {

    @Autowired
    TaskAssignmentMapper taskAssignmentMapper;

    @Autowired
    UserMapper userMapper;

    @ResponseBody
    @RequestMapping(value = "/taskAssignment/findByProjectId")
    public BaseResponse<List<TaskAssignment>> findByProjectId(HttpServletRequest request){
        BaseResponse<List<TaskAssignment>> result = new BaseResponse<>();
        String projectId = String.valueOf(request.getParameter("projectId"));
        List<TaskAssignment> taskAssignmentList = taskAssignmentMapper.findByProjectId(Integer.valueOf(projectId));
        System.out.println(taskAssignmentList);
        result.code = 200;
        result.setData(taskAssignmentList);
        return result;
    }

    /**
     * ajax请求
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/taskAssignment/addTaskAssignment")
    public BaseResponse<TaskAssignment> addProject(HttpServletRequest request) throws Exception{
        BaseResponse<TaskAssignment> result = new BaseResponse<>();
        String taskAssignmentString = URLDecoder.decode(String.valueOf(request.getParameter("taskAssignment")), "UTF-8");
        TaskAssignment taskAssignment = JSONObject.toJavaObject(JSON.parseObject(taskAssignmentString), TaskAssignment.class);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = df2.format(df.parse(taskAssignment.getStartTime().replace("Z", " UTC")));
        String stopTime = df2.format(df.parse(taskAssignment.getStopTime().replace("Z", " UTC")));
        taskAssignment.setStartTime(startTime);
        taskAssignment.setStopTime(stopTime);
        taskAssignment.setFinishedLevel(0);
        System.out.println(taskAssignment);
        taskAssignmentMapper.insert(taskAssignment);
        result.code = 200;
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/taskAssignment/editEngineer")
    public BaseResponse<TaskAssignment> editEngineer(HttpServletRequest request) throws Exception{
        BaseResponse<TaskAssignment> result = new BaseResponse<>();
        String username = URLDecoder.decode(String.valueOf(request.getParameter("username")), "UTF-8");
        String departmentId = String.valueOf(request.getParameter("departmentId"));
        String selectTaskId = String.valueOf(request.getParameter("selectTaskId"));
        String selectUserRole = String.valueOf(request.getParameter("selectUserRole"));//designer reviewer inspector validationer

        List<User> userList = userMapper.findByUserNameAndDeptId(username, Integer.valueOf(departmentId));
        Integer userID  = null;
        if(userList != null && userList.size() > 0){

        }

        //TODO

        return null;


    }


}

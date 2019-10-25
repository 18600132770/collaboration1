package com.huag.collaboration.controller;

import com.huag.collaboration.entities.TaskAssignment;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.TaskAssignmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/25 11:35
 */
@Controller
public class TaskAssignmentController {

    @Autowired
    TaskAssignmentMapper taskAssignmentMapper;

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


}

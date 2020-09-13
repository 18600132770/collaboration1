package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huag.collaboration.entities.*;
import com.huag.collaboration.entities.base.PageBaseResponse;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.TaskAssignmentMapper;
import com.huag.collaboration.mapper.TaskReviewProcessMapper;
import com.huag.collaboration.mapper.UserMapper;
import com.huag.collaboration.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    TaskReviewProcessMapper taskReviewProcessMapper;

    @ResponseBody
    @RequestMapping(value = "/taskAssignment/findByProjectId")
    public BaseResponse<Object> findByProjectId(HttpServletRequest request){
        BaseResponse<Object> result = new BaseResponse<>();
        int currentPage = RequestUtils.getCurrentPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        String projectId = String.valueOf(request.getParameter("projectId"));
        List<TaskAssignment> taskAssignmentList = taskAssignmentMapper.findByProjectId(Integer.valueOf(projectId));
        PageInfo<TaskAssignment> pageInfo = new PageInfo<>(taskAssignmentList, pageSize);

        int pages = pageInfo.getPages();
        long total = pageInfo.getTotal();
        PageBaseResponse response = new PageBaseResponse(taskAssignmentList, currentPage,
                pageSize, pages, total);
        result.code = 200;
        result.setData(response);
        return result;
    }

    /**
     * 分项目添加任务
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
        taskAssignmentMapper.insert(taskAssignment);

        int taskAssignmentId = taskAssignment.getId();

        /**
         * 创建任务时，插入审批流程
         */
        Configueations configueations = new Configueations();
        String[] taskReviewProcessRoles = configueations.getTaskReviewProcessRoles();
        for (int i = 0; i < taskReviewProcessRoles.length; i ++){
            TaskReviewProcess taskReviewProcess = new TaskReviewProcess();
            taskReviewProcess.setTaskAssignmentId(taskAssignmentId);
            taskReviewProcess.setRole(taskReviewProcessRoles[i]);
            taskReviewProcess.setOrderNum(i);
            taskReviewProcessMapper.insert(taskReviewProcess);
        }

        result.code = 200;
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/taskAssignment/editEngineer")
    public BaseResponse<TaskAssignment> editEngineer(HttpServletRequest request) throws Exception{
        BaseResponse<TaskAssignment> result = new BaseResponse<>();
        String username = URLDecoder.decode(String.valueOf(request.getParameter("username")), "UTF-8").replace("\"", "");
        String departmentId = String.valueOf(request.getParameter("departmentId"));
        String selectTaskId = String.valueOf(request.getParameter("selectTaskId"));
        String selectUserRole = String.valueOf(request.getParameter("selectUserRole"));//designer reviewer inspector validationer
        List<User> userList = userMapper.findByUserNameAndDeptId(username, Integer.valueOf(departmentId));

        if(userList != null && userList.size() > 0){
            User user = userList.get(0);
            System.out.println(user);
            TaskAssignment taskAssignment = taskAssignmentMapper.findById(Integer.valueOf(selectTaskId));
            if("designer".equals(selectUserRole)){
                taskAssignment.setDesignerId(user.getId());
            }else if("reviewer".equals(selectUserRole)){
                taskAssignment.setReviewerId(user.getId());
            }else if("inspector".equals(selectUserRole)){
                taskAssignment.setInspectorId(user.getId());
            }else if("validationer".equals(selectUserRole)){
                taskAssignment.setValidationerId(user.getId());
            }
            taskAssignmentMapper.update(taskAssignment);
        }
        result.setCode(200);
        return result;


    }

    @ResponseBody
    @RequestMapping(value = "/taskAssignment/findById")
    public BaseResponse<TaskAssignment> findFilesByProjectId(HttpServletRequest request){
        BaseResponse<TaskAssignment> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        if(StringUtils.isNotBlank(id)){
            TaskAssignment taskAssignment = taskAssignmentMapper.findById(Integer.valueOf(id));
            result.setData(taskAssignment);
        }
        result.code = 200;
        return result;
    }


    /**
     * 删除任务
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/taskAssignment/delete")
    public BaseResponse<TaskAssignment> deleteTaskAssigment(HttpServletRequest request){
        BaseResponse<TaskAssignment> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        if(StringUtils.isNotBlank(id)){
            TaskAssignment taskAssignment = taskAssignmentMapper.findById(Integer.valueOf(id));
            taskAssignment.setDeltag("1");
            taskAssignmentMapper.update(taskAssignment);
        }
        result.code = 200;
        return result;
    }


}

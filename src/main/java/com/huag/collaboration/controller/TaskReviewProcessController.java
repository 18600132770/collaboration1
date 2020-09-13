package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huag.collaboration.entities.ProjectSummary;
import com.huag.collaboration.entities.TaskAssignment;
import com.huag.collaboration.entities.TaskReviewProcess;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.TaskReviewProcessMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;

/**
 * 任务审批流程
 * @author huag
 * @date 2020/9/13 4:50 下午
 */
@Controller
public class TaskReviewProcessController {

    @Autowired
    TaskReviewProcessMapper taskReviewProcessMapper;

    /**
     * 根据任务id，查询送审流程（点击送审按钮，查询送审流程）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/taskReviewProcess/findByTaskAssignmentId")
    public BaseResponse<List<TaskReviewProcess>> findFilesByProjectId(HttpServletRequest request){
        BaseResponse<List<TaskReviewProcess>> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        if(StringUtils.isNotBlank(id)){
            List<TaskReviewProcess> taskReviewProcesseList = taskReviewProcessMapper.findByTaskAssignmentId(Integer.valueOf(id));
            result.setData(taskReviewProcesseList);
        }
        result.code = 200;
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/taskReviewProcess/update")
    public BaseResponse<TaskReviewProcess> updateTaskReviewProcess(HttpServletRequest request) throws Exception{
        BaseResponse<TaskReviewProcess> result = new BaseResponse<>();
        String projectStr = URLDecoder.decode(String.valueOf(request.getParameter("projectSummary")), "UTF-8");
        TaskReviewProcess taskReviewProcess = JSONObject.toJavaObject(JSON.parseObject(projectStr), TaskReviewProcess.class);
        if(taskReviewProcess.getId() == null || "".equals(taskReviewProcess.getId())){
            taskReviewProcessMapper.insert(taskReviewProcess);
        }else{
            taskReviewProcessMapper.update(taskReviewProcess);
            //TODO 如果用户点击了驳回，那么审批流程从头开始。例如： 设计->复核->审核->审定  更新为：设计->复核->设计->复核->审核->审定。
            Integer taskAssignmentId = taskReviewProcess.getTaskAssignmentId(); //任务id
            Integer id = taskReviewProcess.getId();//审批流程id
            List<TaskReviewProcess> taskReviewProcessList = taskReviewProcessMapper.findByTaskAssignmentId(taskAssignmentId);
            int orderNum = 0;
            for (int i = 0; i < taskReviewProcessList.size(); i ++){
                TaskReviewProcess javaBean = taskReviewProcessList.get(i);
                if(javaBean.getId() == id){
                    //TODO 判断本次是拒绝了吗，上次是否也是拒绝，如果上次是空，本次是拒绝，那么下边另加流程
                }
            }
        }
        result.code = 200;
        result.setData(taskReviewProcess);
        return result;
    }

}

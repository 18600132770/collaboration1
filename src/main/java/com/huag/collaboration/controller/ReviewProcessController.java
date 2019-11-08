package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huag.collaboration.entities.Profile;
import com.huag.collaboration.entities.ReviewProcess;
import com.huag.collaboration.entities.TaskAssignment;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ReviewProcessMapper;
import com.huag.collaboration.mapper.TaskAssignmentMapper;
import com.huag.collaboration.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author huag
 * @date 2019/11/8 9:45
 */
@Controller
public class ReviewProcessController {

    @Autowired
    ReviewProcessMapper reviewProcessMapper;

    @Autowired
    TaskAssignmentMapper taskAssignmentMapper;


    /**
     * TODO 只上传了文件名，还没上传文件内容
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reviewProcess/uploadFile")
    public BaseResponse<List<Profile>> upload(HttpServletRequest request){
        BaseResponse<List<Profile>> result = new BaseResponse<>();

        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file = multipartRequest.getFile("file");
        String filename = file.getOriginalFilename();

        System.out.println(filename);

        String taskAssignmentId = String.valueOf(request.getParameter("taskAssignmentId"));
        System.out.println("taskAssignmentId: " + taskAssignmentId);

        List<ReviewProcess> reviewProcessList = reviewProcessMapper.findByTaskAssignmentId(Integer.valueOf(taskAssignmentId));
        if(reviewProcessList != null && reviewProcessList.size() > 0){
            ReviewProcess reviewProcess = reviewProcessList.get(0);
            reviewProcess.setFileNames(reviewProcess.getFileNames() + "\n" + filename);
            reviewProcessMapper.update(reviewProcess);
        }else{
            ReviewProcess reviewProcess = new ReviewProcess();
            reviewProcess.setFileNames(filename);
            reviewProcess.setDesignerCreateTime(DateUtils.getDateIn_yyyyMMddHHmmss());
            reviewProcess.setTaskAssignmentId(Integer.valueOf(taskAssignmentId));
            reviewProcessMapper.insert(reviewProcess);
        }

//        OSSUtils.uploadString("profileTree/" + projectId + "/" + filename, "测试数据，以后再用真实数据");

        result.code = 200;
        result.setData(null);
        return result;
    }

    /**
     * 根据taskAssignmentId查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reviewProcess/findByTaskAssignmentId")
    public BaseResponse<ReviewProcess> findByTaskAssignmentId(HttpServletRequest request){
        BaseResponse<ReviewProcess> result = new BaseResponse<>();
        String taskAssignmentId = String.valueOf(request.getParameter("taskAssignmentId"));
        List<ReviewProcess> reviewProcessList = reviewProcessMapper.findByTaskAssignmentId(Integer.valueOf(taskAssignmentId));
        if(reviewProcessList != null && reviewProcessList.size() > 0){
            result.setData(reviewProcessList.get(0));
        }
        result.code = 200;
        return result;
    }

    /**
     * 更新保存送审内容
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reviewProcess/saveReviewProcessData")
    public BaseResponse<ReviewProcess> saveReviewProcessData(HttpServletRequest request) throws Exception{
        BaseResponse<ReviewProcess> result = new BaseResponse<>();
        String reviewProcessData = URLDecoder.decode(String.valueOf(request.getParameter("reviewProcessData")), "UTF-8");
        String taskAssignmentId = String.valueOf(request.getParameter("taskAssignmentId"));
        ReviewProcess reviewProcess = JSONObject.toJavaObject(JSON.parseObject(reviewProcessData), ReviewProcess.class);

        if(reviewProcess != null && reviewProcess.getTaskAssignmentId() == null){
            reviewProcess.setTaskAssignmentId(Integer.valueOf(taskAssignmentId));
            int id = reviewProcessMapper.insert(reviewProcess);
            reviewProcess.setId(id);
        }

        TaskAssignment taskAssignment = taskAssignmentMapper.findById(Integer.valueOf(taskAssignmentId));

        if(reviewProcess != null && StringUtils.isNotBlank(reviewProcess.getValidationerOpioion())){
            taskAssignment.setFinishedLevel(4);
        }else if(reviewProcess != null && StringUtils.isNotBlank(reviewProcess.getInspectorOpionion())){
            taskAssignment.setFinishedLevel(3);
        }else if(reviewProcess != null && StringUtils.isNotBlank(reviewProcess.getReviewerOpioion())){
            taskAssignment.setFinishedLevel(2);
        }else if(reviewProcess != null && StringUtils.isNotBlank(reviewProcess.getDesignerOpinion())){
            taskAssignment.setFinishedLevel(1);
        }
        taskAssignmentMapper.update(taskAssignment);

        reviewProcessMapper.update(reviewProcess);
        result.code = 200;
        return result;
    }

}

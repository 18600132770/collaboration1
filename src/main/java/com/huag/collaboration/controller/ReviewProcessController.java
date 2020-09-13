package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huag.collaboration.entities.fileTree.Profile;
import com.huag.collaboration.entities.ReviewProcess;
import com.huag.collaboration.entities.TaskAssignment;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ReviewProcessMapper;
import com.huag.collaboration.mapper.TaskAssignmentMapper;
import com.huag.collaboration.utils.DateUtils;
import com.huag.collaboration.utils.OSSUtils;
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
import java.nio.charset.StandardCharsets;
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
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reviewProcess/uploadFile")
    public BaseResponse<List<Profile>> upload(HttpServletRequest request) throws Exception{
        BaseResponse<List<Profile>> result = new BaseResponse<>();
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file = multipartRequest.getFile("file");
        String filename = file.getOriginalFilename();
        byte[] content = file.getBytes();

        String taskAssignmentId = String.valueOf(request.getParameter("taskAssignmentId"));
        String oosFileUrl = "reviewProcess/" + taskAssignmentId + "/" + filename;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", filename);
        jsonObject.put("url", oosFileUrl);

        List<ReviewProcess> reviewProcessList = reviewProcessMapper.findByTaskAssignmentId(Integer.valueOf(taskAssignmentId));
        if(reviewProcessList != null && reviewProcessList.size() > 0){
            ReviewProcess reviewProcess = reviewProcessList.get(0);
            String fileField = reviewProcess.getFile();
            if(StringUtils.isNotBlank(fileField)){
                JSONArray jsonArray = JSONArray.parseArray(fileField);
                jsonArray.add(jsonObject);
                reviewProcess.setFile(jsonArray.toJSONString());
                System.out.println(jsonArray.toJSONString());
                reviewProcessMapper.update(reviewProcess);
            }else{
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(jsonObject);
                reviewProcess.setFile(jsonArray.toJSONString());
                System.out.println(jsonArray.toJSONString());
                reviewProcessMapper.update(reviewProcess);
            }

        }else{
            ReviewProcess reviewProcess = new ReviewProcess();
            reviewProcess.setDesignerCreateTime(DateUtils.getDateIn_yyyyMMddHHmmss());
            reviewProcess.setTaskAssignmentId(Integer.valueOf(taskAssignmentId));
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            reviewProcess.setFile(jsonArray.toJSONString());
            reviewProcessMapper.insert(reviewProcess);
        }

        System.out.println("--------------------- 上传文件");
        System.out.println(oosFileUrl);

        String str = new String(content, StandardCharsets.UTF_8);
        OSSUtils.uploadString(oosFileUrl, str);

        result.code = 200;
        result.setData(null);
        return result;
    }

    /**
     * 根据taskAssignmentId查询送审流程
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

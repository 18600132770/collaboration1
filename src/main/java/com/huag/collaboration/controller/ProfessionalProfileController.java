package com.huag.collaboration.controller;

import com.huag.collaboration.entities.ProfessionalProfile;
import com.huag.collaboration.entities.UpperProfessionData;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ProfessionalProfileMapper;
import com.huag.collaboration.mapper.UpperProfessionDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/28 15:53
 */
@Controller
public class ProfessionalProfileController {

    @Autowired
    ProfessionalProfileMapper professionalProfileMapper;

    /**
     * 页面跳转到上序资料
     * @param model
     * @return
     */
    @GetMapping("/previousProfiles")
    public String list(Model model){
        return "previousProfile/list";
    }

    /**
     * 查找我收到的上序资料（上序专业主动发送给我的资料）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/previousProfile/findMyReceivedPreviousProfile")
    public BaseResponse<List<ProfessionalProfile>> findMyReceivedPreviousProfile(HttpServletRequest request){
        BaseResponse<List<ProfessionalProfile>> result = new BaseResponse<>();
        String projectId = request.getParameter("projectId");
        List<ProfessionalProfile> myReceivedPreviousProfile = professionalProfileMapper.findMyReceivedPreviousProfile(Integer.valueOf(projectId));
        System.out.println(projectId);
        System.out.println(myReceivedPreviousProfile);
        result.code = 200;
        result.setData(myReceivedPreviousProfile);
        return result;
    }

    /**
     * 查找我发起的上序资料（我需要我的上序专业给我提供的资料）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/previousProfile/findMyInitiatedPreviousProfile")
    public BaseResponse<List<ProfessionalProfile>> findMyInitiatedPreviousProfile(HttpServletRequest request){
        BaseResponse<List<ProfessionalProfile>> result = new BaseResponse<>();
        String projectId = request.getParameter("projectId");
        List<ProfessionalProfile> myReceivedPreviousProfile = professionalProfileMapper.findMyInitiatedPreviousProfile(Integer.valueOf(projectId));
        result.code = 200;
        result.setData(myReceivedPreviousProfile);
        return result;
    }

    /**
     * 查找我提交的发送给下序专业的资料
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/previousProfile/findMySubmitProfileToNextProfession")
    public BaseResponse<List<ProfessionalProfile>> findMySubmitProfileToNextProfession(HttpServletRequest request){
        BaseResponse<List<ProfessionalProfile>> result = new BaseResponse<>();
        String projectId = request.getParameter("projectId");
        List<ProfessionalProfile> myReceivedPreviousProfile = professionalProfileMapper.findMySubmitProfileToNextProfession(Integer.valueOf(projectId));
        result.code = 200;
        result.setData(myReceivedPreviousProfile);
        return result;
    }

    /**
     * 查找下序专业发起的需要我提供的资料
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/previousProfile/findMyNeededProfileToNextProfession")
    public BaseResponse<List<ProfessionalProfile>> findMyNeededProfileToNextProfession(HttpServletRequest request){
        BaseResponse<List<ProfessionalProfile>> result = new BaseResponse<>();
        String projectId = request.getParameter("projectId");
        List<ProfessionalProfile> myReceivedPreviousProfile = professionalProfileMapper.findMyNeededProfileToNextProfession(Integer.valueOf(projectId));
        result.code = 200;
        result.setData(myReceivedPreviousProfile);
        return result;
    }







}

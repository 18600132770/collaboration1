package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huag.collaboration.entities.ProfessionalProfile;
import com.huag.collaboration.entities.Profile;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ProfessionalProfileMapper;
import com.huag.collaboration.mapper.ProfileMapper;
import com.huag.collaboration.mapper.ProjectMapper;
import com.huag.collaboration.utils.OSSUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 专业文件
 * @author huag
 * @date 2019/10/28 15:53
 */
@Controller
public class ProfessionalProfileController {

    @Autowired
    ProfessionalProfileMapper professionalProfileMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProfileMapper profileMapper;

    final StringBuilder fileNameAppend = new StringBuilder();
    final StringBuilder filePathAppend = new StringBuilder();

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

    /**
     * 更新
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/previousProfile/update")
    public BaseResponse<List<ProfessionalProfile>> update(HttpServletRequest request) throws Exception{
        BaseResponse<List<ProfessionalProfile>> result = new BaseResponse<>();
        String professionProfileStr = URLDecoder.decode(String.valueOf(request.getParameter("professionProfile")), "UTF-8");
        ProfessionalProfile professionalProfile = JSONObject.toJavaObject(JSON.parseObject(professionProfileStr), ProfessionalProfile.class);
        professionalProfileMapper.update(professionalProfile);
        result.code = 200;
        return result;
    }

    /**
     * 添加
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/previousProfile/add")
    public BaseResponse<List<ProfessionalProfile>> add(HttpServletRequest request) throws Exception{
        BaseResponse<List<ProfessionalProfile>> result = new BaseResponse<>();
        String professionProfileStr = URLDecoder.decode(String.valueOf(request.getParameter("professionProfile")), "UTF-8");
        ProfessionalProfile professionalProfile = JSONObject.toJavaObject(JSON.parseObject(professionProfileStr), ProfessionalProfile.class);
        professionalProfileMapper.insert(professionalProfile);
        result.code = 200;
        return result;
    }

    /**
     * 添加我申请的上序资料
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/previousProfile/addPreviousProfessionProfile")
    public BaseResponse<ProfessionalProfile> addPreviousProfessionProfile(HttpServletRequest request) throws Exception{
        BaseResponse<ProfessionalProfile> result = new BaseResponse<>();
        String content = URLDecoder.decode(String.valueOf(request.getParameter("content")), "UTF-8");
        Integer projectId = Integer.valueOf(request.getParameter("projectId"));

        String[] selectProjectIds = request.getParameterValues("selectProjectIds");//向这些分项目申请资料

        for (String id :
                selectProjectIds) {
            ProfessionalProfile myApplyProfessionalProfile = new ProfessionalProfile();
            myApplyProfessionalProfile.setReceiveActionProjectId(projectId);
            myApplyProfessionalProfile.setInitiatorProjectId(projectId);
            myApplyProfessionalProfile.setContent(content);
            Project project = projectMapper.findById(projectId);
            myApplyProfessionalProfile.setDepartmentId(project.getDepartmentId());
            professionalProfileMapper.insert(myApplyProfessionalProfile);

            ProfessionalProfile professionalProfile = new ProfessionalProfile();
            professionalProfile.setSubmitActionProjectId(Integer.valueOf(id));
            professionalProfile.setInitiatorProjectId(projectId);
            Project project1 = projectMapper.findById(Integer.valueOf(id));
            professionalProfile.setDepartmentId(project1.getDepartmentId());
            professionalProfile.setContent(content);
            professionalProfileMapper.insert(professionalProfile);
        }
        result.code = 200;
        return result;
    }

    /**
     * 添加我发布给别人的下序资料
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/previousProfile/addNextProfessionProfile")
    public BaseResponse<ProfessionalProfile> addNextProfessionProfile(HttpServletRequest request) throws Exception{
        BaseResponse<ProfessionalProfile> result = new BaseResponse<>();
        String content = URLDecoder.decode(String.valueOf(request.getParameter("content")), "UTF-8");
        Integer projectId = Integer.valueOf(request.getParameter("projectId"));

        String[] selectProjectIds = request.getParameterValues("selectProjectIds");//向这些分项目申请资料

        String[] selectProfileIds = request.getParameterValues("selectProfiles");//发给下序的文件

        for (String id :
                selectProfileIds) {
            List<Profile> profileList = profileMapper.findById(Integer.valueOf(id));
            profileList.forEach(profile -> {
                fileNameAppend.append(profile.getName()).append("\n");
                filePathAppend.append(profile.getUrl()).append("\n");
            });
        }

        for (String id :
                selectProjectIds) {
            ProfessionalProfile myApplyProfessionalProfile = new ProfessionalProfile();
            myApplyProfessionalProfile.setSubmitActionProjectId(projectId);
            myApplyProfessionalProfile.setInitiatorProjectId(projectId);
            myApplyProfessionalProfile.setContent(content);
            Project project = projectMapper.findById(projectId);
            myApplyProfessionalProfile.setDepartmentId(project.getDepartmentId());
            myApplyProfessionalProfile.setFileName(fileNameAppend.toString());
            myApplyProfessionalProfile.setFilePath(filePathAppend.toString());
            professionalProfileMapper.insert(myApplyProfessionalProfile);

            ProfessionalProfile professionalProfile = new ProfessionalProfile();
            professionalProfile.setReceiveActionProjectId(Integer.valueOf(id));
            professionalProfile.setInitiatorProjectId(projectId);
            Project project1 = projectMapper.findById(Integer.valueOf(id));
            professionalProfile.setDepartmentId(project1.getDepartmentId());
            professionalProfile.setContent(content);
            professionalProfile.setFileName(fileNameAppend.toString());
            professionalProfile.setFilePath(filePathAppend.toString());
            professionalProfileMapper.insert(professionalProfile);


        }
        result.code = 200;
        return result;
    }


    /**
     * 下载文件
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/previousProfile/dwnSavedFile")
    public void dwnSavedFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = request.getParameter("fileName");
            fileName = URLDecoder.decode(fileName, "UTF-8");
            String fileData = OSSUtils.getFileData(fileName);
            InputStream is = new ByteArrayInputStream(fileData.getBytes());
            OutputStream os = null;
            os = response.getOutputStream();
            IOUtils.copy(is, os);
            response.flushBuffer();
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

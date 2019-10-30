package com.huag.collaboration.controller;

import com.huag.collaboration.entities.ProfessionalProfile;
import com.huag.collaboration.entities.Profile;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.ProfileMapper;
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
import java.util.List;

/**
 * 文件树
 * @author huag
 * @date 2019/10/30 16:20
 */
@Controller
public class ProfileController {

    @Autowired
    ProfileMapper profileMapper;

    @ResponseBody
    @RequestMapping(value = "/profile/findFilesByProjectId")
    public BaseResponse<List<Profile>> findFilesByProjectId(HttpServletRequest request){
        BaseResponse<List<Profile>> result = new BaseResponse<>();
        String projectId = String.valueOf(request.getParameter("projectId"));
        if(StringUtils.isNotBlank(projectId)){
            List<Profile> profileList = profileMapper.findByProjectId(Integer.valueOf(projectId));
            result.setData(profileList);
        }
        result.code = 200;
        return result;
    }

    /**
     * TODO 只上传了文件名，还没上传文件内容
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/profile/uploadFile")
    public BaseResponse<List<Profile>> upload(HttpServletRequest request){
        BaseResponse<List<Profile>> result = new BaseResponse<>();

        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file = multipartRequest.getFile("file");
        String filename = file.getOriginalFilename();

        System.out.println(filename);

        String projectId = String.valueOf(request.getParameter("projectId"));
        System.out.println("projectId: " + projectId);

        Profile profile = new Profile();
        profile.setName(filename);
        profile.setProjectId(Integer.valueOf(projectId));
        profileMapper.insert(profile);

        OSSUtils.uploadString("profileTree/" + projectId + "/" + filename, "测试数据，以后再用真实数据");

        result.code = 200;
        result.setData(null);
        return result;
    }

}

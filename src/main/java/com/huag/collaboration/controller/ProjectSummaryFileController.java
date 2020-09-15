package com.huag.collaboration.controller;

import com.huag.collaboration.entities.fileTree.Profile;
import com.huag.collaboration.entities.fileTree.ProjectSummaryFileTree;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.FileTreeMapper;
import com.huag.collaboration.mapper.ProfileMapper;
import com.huag.collaboration.mapper.ProjectSummaryFileTreeMapper;
import com.huag.collaboration.utils.OSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 项目文件树
 * @author huag
 * @date 2020/9/13 11:13 上午
 */
@Controller
public class ProjectSummaryFileController {

    @Autowired
    FileTreeMapper fileTreeMapper;

    @Autowired
    ProjectSummaryFileTreeMapper projectSummaryFileTreeMapper;

    @Autowired
    ProfileMapper profileMapper;

    /**
     * 根据项目id查询文件树以及文件详情
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/fileTree/findFileTreeByProjectSummaryId")
    public BaseResponse<List<ProjectSummaryFileTree>> findFileTreeByProjectSummaryId(HttpServletRequest request){
        BaseResponse<List<ProjectSummaryFileTree>> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        List<ProjectSummaryFileTree> projectSummaryFileTrees = projectSummaryFileTreeMapper.findByProjectSummaryId(Integer.valueOf(id));
        projectSummaryFileTrees.forEach(projectSummaryFileTree -> {
            Integer fileTreeId = projectSummaryFileTree.getFileTreeId();
            List<Profile> profileList = profileMapper.findByFileTreeId(fileTreeId);
            projectSummaryFileTree.setProfileList(profileList);
        });
        result.code = 200;
        result.setData(projectSummaryFileTrees);
        return result;
    }

    /**
     * 文件树-某项目新增文件夹目录
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/fileTree/addFileTreeOfProjectSummary")
    public BaseResponse<ProjectSummaryFileTree> addFileTreeOfProjectSummary(HttpServletRequest request){
        BaseResponse<ProjectSummaryFileTree> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        String folderName = String.valueOf(request.getParameter("folderName"));
        ProjectSummaryFileTree projectSummaryFileTree = new ProjectSummaryFileTree();
        projectSummaryFileTree.setProjectSummaryId(Integer.valueOf(id));
        projectSummaryFileTree.setName(folderName);
        projectSummaryFileTreeMapper.insert(projectSummaryFileTree);
        result.code = 200;
        return result;
    }

    /**
     * 删除某个项目下的文件树分支
     * 传递参数：分支id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/projectSummary/deleteFileTreeOfProjectSummary")
    public BaseResponse<ProjectSummaryFileTree> deleteFileTreeOfProjectSummary(HttpServletRequest request){
        BaseResponse<ProjectSummaryFileTree> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        projectSummaryFileTreeMapper.delete(Integer.valueOf(id));
        result.code = 200;
        return result;
    }


    /**
     * 项目的文件树的分支上传文件
     * 传递参数：文件内容，项目文件树分支id
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/projectSummaryFileTree/uploadFile")
    public BaseResponse<List<Profile>> upload(HttpServletRequest request) throws Exception{
        BaseResponse<List<Profile>> result = new BaseResponse<>();
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file = multipartRequest.getFile("file");
        String filename = file.getOriginalFilename();
        byte[] content = file.getBytes();
        String id = String.valueOf(request.getParameter("id"));
        Profile profile = new Profile();
        profile.setName(filename);
        profile.setFileTreeId(Integer.valueOf(id));
        String oosFileUrl = "profileTree/" + id + "/" + filename;
        profile.setUrl(oosFileUrl);
        profileMapper.insert(profile);
        String str = new String(content, StandardCharsets.UTF_8);
        OSSUtils.uploadString(oosFileUrl, str);
        result.code = 200;
        result.setData(null);
        return result;
    }

}

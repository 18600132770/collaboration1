package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Department;
import com.huag.collaboration.entities.fileTree.FileTree;
import com.huag.collaboration.entities.fileTree.ProjectSummaryFileTree;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.FileTreeMapper;
import com.huag.collaboration.mapper.ProjectSummaryFileTreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huag
 * @date 2020/8/23 10:23
 */
@Controller
public class FileTreeController {

    @Autowired
    FileTreeMapper fileTreeMapper;

    @Autowired
    ProjectSummaryFileTreeMapper projectSummaryFileTreeMapper;

    @ResponseBody
    @RequestMapping(value = "/fileTree/findAll")
    public BaseResponse<List<FileTree>> findAll(HttpServletRequest request){
        BaseResponse<List<FileTree>> result = new BaseResponse<>();
        List<FileTree> fileTreeList = fileTreeMapper.findAll();
        System.out.println(fileTreeList);
        result.code = 200;
        result.setData(fileTreeList);
        return result;
    }


    /**
     * 删除文件树模板中的分支
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/fileTree/delete")
    public BaseResponse<ProjectSummaryFileTree> deleteFileTree(HttpServletRequest request){
        BaseResponse<ProjectSummaryFileTree> result = new BaseResponse<>();
        String id = String.valueOf(request.getParameter("id"));
        fileTreeMapper.delete(Integer.valueOf(id));
        result.code = 200;
        return result;
    }

}

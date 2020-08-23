package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Department;
import com.huag.collaboration.entities.fileTree.FileTree;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.FileTreeMapper;
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

}

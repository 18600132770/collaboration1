package com.huag.collaboration.controller;

import com.huag.collaboration.mapper.ProjectSubitemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author huag
 * @date 2019/10/8 15:34
 */
@Controller
public class ProjectSubitemController {

    @Autowired
    ProjectSubitemMapper projectSubitemMapper;

    @DeleteMapping("projectSubitem/{id}")
    public String deleteProjectSubitem(@PathVariable("id") Integer id){
        projectSubitemMapper.deleteById(id);
        return "redirect:/project/add";
    }



}

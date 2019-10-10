package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Department;
import com.huag.collaboration.entities.Employee;
import com.huag.collaboration.entities.ProjectSubitem;
import com.huag.collaboration.mapper.ProjectSubitemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

/**
 * @author huag
 * @date 2019/10/8 15:34
 */
@Controller
public class ProjectSubitemController {

    @Autowired
    ProjectSubitemMapper projectSubitemMapper;

    @DeleteMapping("/project/projectSubitem/{id}")
    public String deleteProjectSubitem(@PathVariable("id") Integer id){
        projectSubitemMapper.deleteById(id);
        return "redirect:/project/add";
    }

    @GetMapping("/project/projectSubitem/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model){
        ProjectSubitem projectSubitem = projectSubitemMapper.findById(id);
        model.addAttribute("editProjectSubitem", projectSubitem);
        return "project/add";
    }



}

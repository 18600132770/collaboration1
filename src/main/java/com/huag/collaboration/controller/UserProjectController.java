package com.huag.collaboration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author huag
 * @date 2019/10/16 22:46
 */
@Controller
public class UserProjectController {

    /**
     * 页面跳转
     * @param model
     * @return
     */
    @GetMapping("/userProjects")
    public String  list(Model model){
        return "userProject/list";
    }


}

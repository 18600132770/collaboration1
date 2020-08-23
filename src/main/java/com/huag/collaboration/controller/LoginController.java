package com.huag.collaboration.controller;

import com.github.pagehelper.util.StringUtil;
import com.huag.collaboration.entities.User;
import com.huag.collaboration.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author huag
 * @date 2019/9/26 15:41
 */
@Controller
public class LoginController {

//    @DeleteMapping
//    @PutMapping
//    @GetMapping

    @Autowired
    UserMapper userMapper;

    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map,
                        HttpSession session){
        User user = userMapper.findByCardId(username);
        boolean flag = false;
        if(user != null && StringUtil.isNotEmpty(username) && username.equals(user.getCardId())){
            flag = true;
        }
        if("admin".endsWith(username) && "123456".equals(password)){
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        }else if(flag){
            session.setAttribute("loginUser", user.getCardId());
            return "redirect:/main.html";
        }else{
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }

    @GetMapping("/redirectToMain")
    public String redirectToMain(){
        return "redirect:/main.html";
    }

}


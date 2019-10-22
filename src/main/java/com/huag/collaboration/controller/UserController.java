package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.User;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.UserMapper;
import com.huag.collaboration.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/21 15:15
 */
@Controller
public class UserController {

    @Autowired
    UserMapper userMapper;

    /**
     * ajax请求，询所有可以当负责人的人选
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/findAllPrincipals")
    public BaseResponse<List<User>> findAllPrincipals(HttpServletRequest request){
        String currentDepartmentId = String.valueOf(request.getParameter("currentDepartmentId"));
        BaseResponse<List<User>> result = new BaseResponse<>();
        List<User> userList = userMapper.findAllPrincipals(Integer.valueOf(currentDepartmentId));
        System.out.println(userList);
        result.code = 200;
        result.setData(userList);
        return result;
    }

    /**
     * ajax请求，查询所有可以当总工的人选
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/findAllChiefEngineers")
    public BaseResponse<List<User>> findAllChiefEngineers(HttpServletRequest request){
        String currentDepartmentId = String.valueOf(request.getParameter("currentDepartmentId"));
        BaseResponse<List<User>> result = new BaseResponse<>();
        List<User> userList = userMapper.findAllChiefEngineers(Integer.valueOf(currentDepartmentId));
        System.out.println(userList);
        result.code = 200;
        result.setData(userList);
        return result;
    }


}

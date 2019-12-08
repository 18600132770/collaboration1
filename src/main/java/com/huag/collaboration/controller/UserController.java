package com.huag.collaboration.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.User;
import com.huag.collaboration.entities.base.PageBaseResponse;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.UserMapper;
import com.huag.collaboration.utils.DateUtils;
import com.huag.collaboration.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/21 15:15
 */
@Controller
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/users")
    public String  list(Model model){
        return "user/list";
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/user/{id}")
    public String toEditProject(@PathVariable("id") Integer id, Model model){
        model.addAttribute("userId", id);
        return "user/add";
    }

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

    /**
     * 查找可以担任总项目总负责人的人选
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/findProjectSummaryPrincipals")
    public BaseResponse<List<User>> findProjectSummaryPrincipals(HttpServletRequest request){
        BaseResponse<List<User>> result = new BaseResponse<>();
        List<User> userList = userMapper.findProjectSummaryPrincipals();
        System.out.println(userList);
        result.code = 200;
        result.setData(userList);
        return result;
    }

    /**
     * 查找可以担任总项目总工的人选
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/findProjectSummaryChiefEngineers")
    public BaseResponse<List<User>> findProjectSummaryChiefEngineers(HttpServletRequest request){
        BaseResponse<List<User>> result = new BaseResponse<>();
        List<User> userList = userMapper.findProjectSummaryChiefEngineers();
        System.out.println(userList);
        result.code = 200;
        result.setData(userList);
        return result;
    }

    /**
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user/findAll")
    public BaseResponse<Object> findAll(HttpServletRequest request){
        BaseResponse<Object> result = new BaseResponse<>();

        int currentPage = RequestUtils.getCurrentPage(request);
        int pageSize = RequestUtils.getPageSize(request);

        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<User> userList = userMapper.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(userList, pageSize);

        int pages = pageInfo.getPages();
        long total = pageInfo.getTotal();
        PageBaseResponse response = new PageBaseResponse(userList, currentPage,
                pageSize, pages, total);

        result.code = 200;
        result.setData(JSON.parseObject(JSON.toJSONString(response)));
        return result;
    }

    /**
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/findById")
    public BaseResponse<User> findById(HttpServletRequest request){
        BaseResponse<User> result = new BaseResponse<>();
        Integer id = RequestUtils.getIntegerParam(request, "id");
        User user = userMapper.findById(id);
        result.code = 200;
        result.setData(user);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/user/updateUserTripStateByUsername")
    public BaseResponse<User> updateUserTripStateByUsername(HttpServletRequest request) throws Exception{
        BaseResponse<User> result = new BaseResponse<>();
        String username = URLDecoder.decode(String.valueOf(request.getParameter("username")), "UTF-8");
        String tripState = URLDecoder.decode(String.valueOf(request.getParameter("tripState")).replace(",", "-"), "UTF-8");
        userMapper.updateUserTripStateByUsername(username, tripState);
        result.code = 200;
        result.setData(null);
        return result;
    }


}

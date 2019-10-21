package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Department;
import com.huag.collaboration.entities.UnderProfessionData;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/21 10:07
 */
@Controller
public class DepartmentController {

    @Autowired
    DepartmentMapper departmentMapper;

    @ResponseBody
    @RequestMapping(value = "/department/findAll")
    public BaseResponse<List<Department>> findAll(HttpServletRequest request){
        BaseResponse<List<Department>> result = new BaseResponse<>();
        List<Department> departmentList = departmentMapper.findAll();
        System.out.println(departmentList);
        result.code = 200;
        result.setData(departmentList);
        return result;
    }

}

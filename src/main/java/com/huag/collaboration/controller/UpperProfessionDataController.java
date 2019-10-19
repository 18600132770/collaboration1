package com.huag.collaboration.controller;

import com.huag.collaboration.entities.UpperProfessionData;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.UpperProfessionDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 上序专业资料
 * @author huag
 * @date 2019/10/19 20:09
 */
@Controller
public class UpperProfessionDataController {

    /**
     * 页面跳转
     * @param model
     * @return
     */
    @GetMapping("/upperProfessionDatas")
    public String list(Model model){
        return "upperProfessionData/list";
    }

    @Autowired
    UpperProfessionDataMapper upperProfessionDataMapper;

    @ResponseBody
    @RequestMapping(value = "/upperProfessionData/findAll")
    public BaseResponse<List<UpperProfessionData>> findAll(HttpServletRequest request){
        BaseResponse<List<UpperProfessionData>> result = new BaseResponse<>();
        List<UpperProfessionData> upperProfessionDataList = upperProfessionDataMapper.findAll();
        System.out.println(upperProfessionDataList);
        result.code = 200;
        result.setData(upperProfessionDataList);
        return result;
    }



}

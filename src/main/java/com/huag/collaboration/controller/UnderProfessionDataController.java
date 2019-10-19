package com.huag.collaboration.controller;

import com.huag.collaboration.entities.UnderProfessionData;
import com.huag.collaboration.entities.UpperProfessionData;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.UnderProfessionDataMapper;
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
 * 提下序
 * @author huag
 * @date 2019/10/19 20:43
 */
@Controller
public class UnderProfessionDataController {

    /**
     * 页面跳转
     * @param model
     * @return
     */
    @GetMapping("/underProfessionDatas")
    public String list(Model model){
        return "underProfessionData/list";
    }

    @Autowired
    UnderProfessionDataMapper underProfessionDataMapper;

    @ResponseBody
    @RequestMapping(value = "/underProfessionData/findAll")
    public BaseResponse<List<UnderProfessionData>> findAll(HttpServletRequest request){
        BaseResponse<List<UnderProfessionData>> result = new BaseResponse<>();
        List<UnderProfessionData> underProfessionDataList = underProfessionDataMapper.findAll();
        System.out.println(underProfessionDataList);
        result.code = 200;
        result.setData(underProfessionDataList);
        return result;
    }

}

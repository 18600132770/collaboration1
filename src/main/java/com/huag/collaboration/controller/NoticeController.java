package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Notice;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.NoticeMapper;
import com.huag.collaboration.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huag
 * @date 2019/10/19 18:59
 */
@Controller
public class NoticeController {

    @Autowired
    NoticeMapper noticeMapper;

    /**
     * 页面跳转
     * @param model
     * @return
     */
    @GetMapping("/notices")
    public String list(Model model){
        return "notice/list";
    }

    @ResponseBody
    @RequestMapping(value = "/notice/findAll")
    public BaseResponse<List<Notice>> findAll(HttpServletRequest request){
        BaseResponse<List<Notice>> result = new BaseResponse<>();
        List<Notice> noticeList = noticeMapper.findAll();
        System.out.println(noticeList);
        result.code = 200;
        result.setData(noticeList);
        return result;
    }

}

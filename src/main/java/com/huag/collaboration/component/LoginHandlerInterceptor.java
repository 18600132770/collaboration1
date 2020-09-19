package com.huag.collaboration.component;

import com.huag.collaboration.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查
 * @author huag
 * @date 2019/9/26 14:21
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Object user = request.getSession().getAttribute("loginUser");
//        if(user == null){
//            //未登录，返回登录页面
//            request.setAttribute("msg", "请先登录");
//            System.out.println("还未登录，返回登录界面");
//            request.getRequestDispatcher("/index.html").forward(request, response);
//            return false;
//        }else {
//            //已登录
//            return true;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

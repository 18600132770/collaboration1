package com.huag.collaboration.utils;

import com.huag.collaboration.entities.base.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author huag
 * @date 2019/11/12 16:00
 */
public class RequestUtils {

    /**
     * 获取当前页，默认是1
     * @param request
     * @return
     */
    public static Integer getCurrentPage(HttpServletRequest request){
        String currentPageStr = String.valueOf(request.getParameter("currentPage"));
        if(StringUtils.isNotBlank(currentPageStr) && !"null".equals(currentPageStr)){
            return Integer.valueOf(currentPageStr);
        }else{
            return Constants.CURRENT_PAGE;
        }
    }

    /**
     * 获取pageSize
     * @param request
     * @return
     */
    public static Integer getPageSize(HttpServletRequest request){
        String pageSizeStr = String.valueOf(request.getParameter("pageSize"));
        if(StringUtils.isNotBlank(pageSizeStr) && !"null".equals(pageSizeStr)){
            return Integer.valueOf(pageSizeStr);
        }else{
            return Constants.PAGE_SIZE;
        }
    }

    /**
     * 判断参数是否存在
     * @param request
     * @param param
     * @return
     */
    public static boolean isExist(HttpServletRequest request, String param){
        boolean exist = false;
        Enumeration paraEnum = request.getParameterNames();
        while (paraEnum.hasMoreElements()) {
            String paramInRequest = (String) paraEnum.nextElement();
            if(StringUtils.isNotBlank(param) && param.equals(paramInRequest)){
                exist = true;
            }
        }
        return exist;
    }

    /**
     * 获取整形参数
     * @param request
     * @param param
     * @return
     */
    public static Integer getIntegerParam(HttpServletRequest request, String param){
        String paramInRequest = String.valueOf(request.getParameter(param));
        if(StringUtils.isNotBlank(paramInRequest) && !"null".equals(paramInRequest)){
            try{
                return Integer.valueOf(paramInRequest);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

}

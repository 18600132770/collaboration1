package com.huag.collaboration.utils;

import com.huag.collaboration.entities.base.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

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

}

package com.huag.collaboration.component;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * 给容器加入自定义的ErrorAttributes
 * @author huag
 * @date 2019/9/26 14:31
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(requestAttributes, includeStackTrace);
        map.put("company", "协同五影工作室");

        //异常处理器携带的数据
        Map<String,Object> ext = (Map<String, Object>)requestAttributes.getAttribute("ext", 0);
        map.put("ext", ext);
        return map;

    }
}

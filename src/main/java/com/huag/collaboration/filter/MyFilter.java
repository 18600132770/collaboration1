package com.huag.collaboration.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author huag
 * @date 2019/9/26 15:08
 */
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter process...");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

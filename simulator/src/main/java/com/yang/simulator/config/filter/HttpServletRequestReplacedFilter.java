package com.yang.simulator.config.filter;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**  
 * @title: HttpServletRequestReplacedFilter.java</p>  
 * @description:
 * @author yangfh  
 * @date 2018年4月19日下午5:14:16  
 */
public class HttpServletRequestReplacedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;

        if(request instanceof HttpServletRequest && !StringUtils.isEmpty(request.getContentType())  && !request.getContentType().toLowerCase().contains("multipart/form-data")) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if(null == requestWrapper) {
            chain.doFilter(request, response);
        } else {

            chain.doFilter(requestWrapper, response);
        }
    }
}

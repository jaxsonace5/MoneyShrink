package com.kakao.moneyShrink.interceptor;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ReadableRequestResponseWrapperFilter implements Filter {

    @Override public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        ReadableRequestWrapper requestWrapper = new ReadableRequestWrapper((HttpServletRequest) servletRequest);

        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override public void destroy() {

    }
}

package com.smarthome.AIHome.filter;

import com.smarthome.AIHome.entity.ApiResponse;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String urls [] = {"/login", "register"};
        String url = req.getRequestURL().toString();

        for(String s : urls){
            if(url.contains(s)){
                filterChain.doFilter(request,response);
                return;
            }
        }


        HttpSession session =req.getSession();
        Object user = session.getAttribute("currentUser");
        if(user != null){
            filterChain.doFilter(request,response);
        }else {
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.setCode(401);
            apiResponse.setMessage("用户未登录");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}

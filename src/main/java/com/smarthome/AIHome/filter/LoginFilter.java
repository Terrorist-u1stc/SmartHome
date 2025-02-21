package com.smarthome.AIHome.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthome.AIHome.entity.ApiResponse;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String urls [] = {"/login", "register"};
        String url = req.getRequestURL().toString();

        for(String s : urls){
            if(url.contains(s)){
                filterChain.doFilter(request,response);
                return;
            }
        }


        HttpSession session =req.getSession(false);
        Object user = null;
        if(session != null){
         user = session.getAttribute("currentUser");
        }
        if(user != null){
            filterChain.doFilter(request,response);
        }else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 未授权
            resp.setContentType("application/json;charset=UTF-8");

            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.setCode(401);
            apiResponse.setMessage("用户未登录");

            resp.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
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

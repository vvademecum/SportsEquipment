package com.example.Acrobatum.handlers;

import com.example.Acrobatum.service.AuthService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated
public class RequestHandler extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        if(modelAndView != null) {
            String role = AuthService.getRole();
            modelAndView.getModelMap().addAttribute("userRole", role);
        }
    }
}

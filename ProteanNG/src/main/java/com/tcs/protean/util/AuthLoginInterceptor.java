package com.tcs.protean.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//로그인 처리를 담당하는 인터셉터
public class AuthLoginInterceptor extends HandlerInterceptorAdapter{

 @Override
 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     //session 객체 갖고옴
     HttpSession session = request.getSession();

    String requestUrl = request.getRequestURL().toString();     
     //하단의 Url 체크를 통해 예외처리를 해줌으로써 무한 리디렉션에서 벗어남
     if(requestUrl.contains("login")){
    	 return true;
     }
     
     //login처리를 담당하는 사용자 정보를 담고 있는 객체를 가져옴
     Object obj = session.getAttribute("member");
     if ( obj == null ){    	 
         //로그인이 안되어 있는 경우 로그인 페이지로 돌려 보낸다
         response.sendRedirect("login");
         return false;
     }
       
     try {
         //admin이라는 세션key를 가진 정보가 있으면 admin페이지로 이동
         if(session.getAttribute("admin") !=null ){
                 response.sendRedirect("/admin");
                 return true;
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
     return true;
 }

 // 컨트롤러가 수행되고 화면이 보여지기 직전에 수행되는 메서드
 @Override
 public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
     super.postHandle(request, response, handler, modelAndView);
 }     
 
}
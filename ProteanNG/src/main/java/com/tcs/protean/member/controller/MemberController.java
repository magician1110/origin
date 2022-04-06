package com.tcs.protean.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcs.protean.member.model.vo.Member;
import com.tcs.protean.member.service.MemberService;



@Controller
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject // 자동 주입
	private MemberService memberService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("Home.");		
		return "member/login";
	}
	
//	@RequestMapping(value = "login", method = RequestMethod.POST)
//	public void loginMember(HttpServletRequest request, HttpServletResponse response, Member m, HttpSession session) throws Exception {
//		logger.info("login() call...");
//		PrintWriter out = response.getWriter();
//		
//		m.setId(request.getParameter("id"));
//		m.setPassword(request.getParameter("password"));
//		Member member = memberService.loginMember(m);
//		if (member != null) {
//			session.setAttribute("member", member);
//			out.print("true");
//		} else {
//			session.setAttribute("member", null);
//			out.print("false");
//		}
//		out.flush();
//		out.close();
//	}
	
    // 로그인 처리하는 부분
    @RequestMapping(value="login")
    public void loginCheck(HttpSession session, @ModelAttribute("m") Member m, HttpServletRequest request, HttpServletResponse response) throws Exception{

    	//HTTP_REFERER 체크하기
        String beforeUrl=request.getHeader("Referer");
        session.setAttribute("Referer",beforeUrl);
        
        	System.out.println("==============BEFORE==============="+beforeUrl+"==========================");
        
    	//ajax에서 왔는지 확인 */
        	
    	if("true".equals(request.getHeader("AJAX"))) {
    		System.out.println("==============AJAX==============="+request.getHeader("AJAX")+"========================");
    	}
    	
    	//해당 로직은 인증관련 처리 (servlet-context, web.xml 선 처리 필요, AuthLoginInterceptor는 현재 사용 X)
    	String reqUrl = request.getRequestURL().toString();
    	System.out.println("-----------------> Url check Interceptor , reqUrl : " +reqUrl );
    	
    	//if(reqUrl.equals("특정url")){
    		//return false;
    	//}
    	//ajax방식이 아닐때 사용
        //String returnURL = "";
        if (session.getAttribute("member") != null ) {	
            //기존에 login 세션 값이 존재한다면
        	//기존값을 제거해 준다.        	
            session.removeAttribute("member");
        }
         
        //로그인이 성공하면 UsersVO 객체를 반환함.
        m.setId(request.getParameter("id"));
        m.setPassword(request.getParameter("password"));
        
        Member loginUser = memberService.loginMember(m);
        
        if (loginUser != null) {
        	System.out.println("============test===========로그인성공=============================");
        	//로그인 성공
        	//세션에 login인이란 이름으로 UsersVO 객체를 저장해 놈.
            session.setAttribute("member", m);
            //로그인 성공시 메인페이지로 이동하지만 ajax방식이 아닐때 사용
            //returnURL = "redirect:/index";
            
            try {
                response.getWriter().write("true");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
        	//로그인에 실패한 경우
        	//로그인 폼으로 다시 가도록 함  ajax방식이 아닐때 사용
            //returnURL = "redirect:/login/loginForm";
        	
            try {
                response.getWriter().write("false");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //위에서 설정한 returnURL 을 반환해서 이동시킴
        //return returnURL;
    }
 
//    //로그아웃하는 부분
//    @RequestMapping(value="logout")
//    public String logout(HttpSession session) {    	
//    	//로그아웃후 로그인 화면으로 이동
//        session.invalidate();        
//        return "redirect:login.do";
//    }
	@RequestMapping(value= "logout")
	public String logoutMember(HttpSession session, HttpServletRequest request) {
		logger.info("logoutMember() call...");
		String url = request.getHeader("referer");
		
		if (session.getAttribute("member") != null) {
			session.invalidate();
		}

		return "redirect:" + url;
	}
	
}



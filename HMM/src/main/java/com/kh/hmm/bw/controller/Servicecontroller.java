package com.kh.hmm.bw.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hmm.board.model.service.BoardService;
import com.kh.hmm.board.model.vo.Board;
import com.kh.hmm.bw.model.service.ServiceService;
import com.kh.hmm.bw.model.vo.Service;
import com.kh.hmm.member.model.vo.Member;

@Controller
public class Servicecontroller {
	
	@Autowired
	private ServiceService sService;
	
	@Autowired
	private JavaMailSender mailSender; // xml에 등록한 bean autowired
	
	// 메일 발송도 같이 함
	@RequestMapping(value = "bwlist.do", method = RequestMethod.POST)
	public String bwlist(Service s) throws FileNotFoundException, URISyntaxException {

		System.out.println(s);
		sService.insertService(s);
		try{
			  SimpleMailMessage message = new SimpleMailMessage();
			 
			  message.setFrom(s.getSername());
			  message.setTo("wkdgma91@gmail.com");
			  if(s.getSercontent().length() > 8){
				  message.setSubject("["+s.getSername()+"] : "+s.getSercontent().substring(0, 7));
			  } else {
				  message.setSubject("["+s.getSername()+"] : "+s.getSercontent());
			  }
			  message.setText(s.getSercontent());
			   
			  mailSender.send(message);
			 
			 }catch(Exception e){
			  e.printStackTrace();
			 }  
		return "../../index";
	} 
	
/*	@RequestMapping(value = "adminlist.do")
	public String adminlist(Model model) {
	
		ArrayList<Service> slist = sService.selectAll();
		model.addAttribute("slist",slist);
		model.addAttribute("slsize",slist.size());
		return "../../adminpage";
	
	}*/
	
	@RequestMapping(value = "checkboxlist.do", method=RequestMethod.GET)
/*	@ResponseBody*/
	public String testCheck(String[] valueArrTest){
		System.out.println(valueArrTest);
		sService.checkBox(valueArrTest);
		return "redirect:adminlist.do";
	
	}
	@RequestMapping(value = "servicecheck.do", method=RequestMethod.GET)
	@ResponseBody
	public String serviceCheck(String valueArrTest){
		String ch = null;
		int rr = sService.serviceCheck(valueArrTest);
		if(rr > 0){
			ch = "yes";
			//int ti = sService.serviceCount(title,id);
		}
		System.out.println(ch);
		return ch;
	}
	
	@RequestMapping(value = "adminlist.do")
	   public String reviewList(Model mv, HttpServletRequest request){

	      int currentPage = 1;
	      // 현재 페이지 지정

	      if (request.getParameter("page") != null) {
	         currentPage = Integer.parseInt(request.getParameter("page"));
	      }

	      int countList = 8;
	      // 한 페이지당 보여줄 글의 수 지정

	      int totalRow = sService.totalRow();
	      // 전체 글의 수 지정

	      int maxPage = totalRow / countList;
	      // 총 페이지

	      if (totalRow % countList > 0) {
	         maxPage++;
	      }

	      int pageCount = 5;
	      // 아래 보여줄 페이지의 수
	      int startPage = ((currentPage - 1) / pageCount) * pageCount + 1;
	      // 시작 페이지
	      int endPage = startPage + pageCount - 1;
	      // 마지막 페이지
	      if (endPage > maxPage) {
	         endPage = maxPage;
	      }

	      int startNo = (currentPage - 1) * countList + 1;
	      // 시작 rownum
	      int endNo = startNo + countList - 1;

	      HashMap<String, Integer> map = new HashMap<String, Integer>();

	      map.put("startNo", startNo);
	      map.put("endNo", endNo);

	      ArrayList<Service> slist = sService.sSelectAll(map);
	      ArrayList<Service> slistAll = sService.superSelectAll();
	      
	      int ti = sService.serviceCount();
	      
	  	  mv.addAttribute("slist",slist); // 게시판 페이징 처리
	      mv.addAttribute("slsize",slistAll.size()); //페이지 카운트 되는 것!ㅋㅋㅋ
	      mv.addAttribute("currentPage", currentPage);
	      mv.addAttribute("startPage", startPage);
	      mv.addAttribute("endPage", endPage);
	      mv.addAttribute("maxPage", maxPage);
	      mv.addAttribute("yCnt", ti);  // 총 게시글 대비  Y 카운트
	      mv.addAttribute("nCnt", slistAll.size() - ti);  // 총 게시글 대비  N 카운트

	      return "../../adminpage";
	
}
}

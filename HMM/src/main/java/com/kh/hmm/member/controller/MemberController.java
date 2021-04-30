package com.kh.hmm.member.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hmm.member.model.service.MemberService;
import com.kh.hmm.member.model.vo.Member;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public void loginMember(HttpServletRequest request, HttpServletResponse response, Member m, HttpSession session)
			throws Exception {
		logger.info("login() call...");
		PrintWriter out = response.getWriter();
		m.setId(request.getParameter("id"));
		m.setPassword(request.getParameter("password"));
		Member member = memberService.loginMember(m);
		if (member != null) {
			session.setAttribute("member", member);
			out.print("true");
		} else {
			session.setAttribute("member", null);
			out.print("false");
		}
		out.flush();
		out.close();
	}

	@RequestMapping("logout.do")
	public String logoutMember(HttpSession session, HttpServletRequest request) {
		logger.info("logoutMember() call...");
		String url = request.getHeader("referer");
		if (session.getAttribute("member") != null) {
			session.invalidate();
		}

		return "redirect:" + url;
	}

	@RequestMapping(value = "enroll.do", method = RequestMethod.POST)
	public String memberInsert(Member m, HttpSession session) {
		logger.info("memberInsert() call...");
		System.out.println(m);
		Member member = memberService.enrollMember(m);
		if (member != null)
			session.setAttribute("member", member);
		return "redirect:/";
	}

	@RequestMapping(value = "google.do", method = RequestMethod.POST)
	public void googleInsert(HttpServletRequest request, HttpServletResponse response, Member m, HttpSession session)
			throws IOException {
		logger.info("googleInsert() call...");
		System.out.println(m);

		PrintWriter out = response.getWriter();
		m.setId(request.getParameter("id"));
		m.setPassword(request.getParameter("password"));
		m.setEmail(request.getParameter("email"));
		m.setJob(request.getParameter("job"));
		m.setPhoto(request.getParameter("photo"));

		Member member = memberService.googleMember(m);
		System.out.println("구글 멤버 세션 값 : " + member);

		if (member != null) {
			session.setAttribute("member", member);
			out.print("true");
		} else {
			session.setAttribute("member", null);
			out.print("false");
		}
		out.flush();
		out.close();

	}

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String memberUpdate(Member m, HttpSession session, HttpServletRequest request) {
		logger.info("memberUpdate() call...");
		String url = request.getHeader("referer");
		Member member = memberService.updateMember(m);
		if (member != null) {
			session.setAttribute("member", member);

		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "deleteMember.do", method = RequestMethod.GET)
	public String memberDelete(HttpSession session, HttpServletRequest request) {
		logger.info("memberDelete() call...");
		String url = request.getHeader("referer");
		String memberId = ((Member) (session.getAttribute("member"))).getId();
		Member member = memberService.deleteMember(memberId);
		System.out.println("delete : " + member);
		if (member != null) {
			session.setAttribute("member", member);
		}

		return "redirect:" + url;
	}

	@RequestMapping(value = "profileImgDel.do", method = RequestMethod.GET)
	public String profileImgDel(HttpSession session, HttpServletRequest request, String membercode) {
		logger.info("프로필 사진 삭제 컨트롤러() call...");
		Member member = memberService.profileImgDel(membercode);
		if (member != null) {
			String savePath = "C:\\Hmm\\Hmm\\src\\main\\webapp\\resources\\img\\" + member.getId();
			File file = new File(savePath);
			file.delete();
			session.setAttribute("member", member);
		}
		return "redirect:/updateProfile.do";
	}
	
	@RequestMapping(value = "uploadFile.do", method = RequestMethod.POST)
	public String memberUpdate(HttpServletRequest request, HttpSession session,
			@RequestParam("photo") MultipartFile uploadfile) {
		logger.info("memberUploadProfile() call...");
		if (uploadfile.isEmpty()) {
			System.out.println("파일이 비어있음");
			return "member/updateMember";
		} else {
			Member m = (Member) session.getAttribute("member");
			Member member = null;
			String savePath = "C:\\Hmm\\Hmm\\src\\main\\webapp\\resources\\img\\" + m.getId(); // 파일이 저장될 프로젝트 안의 폴더 경로
			// String savePath = "resources/img/"+m.getId();

			// 파일 객체 생성
			File file = new File(savePath);
			// !표를 붙여주어 파일이 존재하지 않는 경우의 조건을 걸어줌
			if (!file.exists()) {
				// 디렉토리 생성 메서드
				file.mkdirs();
				System.out.println("created directory successfully!");
			} else {
				if (file.exists())
					file.delete();
			}

			String originalFilename = uploadfile.getOriginalFilename(); // fileName.jpg
			String extension = originalFilename.substring(originalFilename.indexOf(".")); // .jpg

			String rename = m.getId() + extension;

			String fullPath = savePath + "\\" + rename;
			if (!uploadfile.isEmpty()) {
				try {
					byte[] bytes = uploadfile.getBytes();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
					stream.write(bytes);
					stream.close();

					fullPath = "resources\\img\\" + m.getId() + "\\" + rename;
					m.setPhoto(fullPath);
					member = memberService.updatePhoto(m);

					if (member != null) {
						session.setAttribute("member", member);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return "redirect:/updateProfile.do";
		}
	}

	@RequestMapping(value = "updateProfile.do", method = RequestMethod.GET)
	public String goUpdateProfile(Model model) {
		logger.info("프로필 수정 페이지로 이동....");
		return "member/updateMember";
	}

	@RequestMapping(value = "idCheck.do", method = RequestMethod.POST)
	public void idCheck(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		System.out.println("idCheck() call....");
		PrintWriter out = response.getWriter();
		int chk = 0;
		System.out.println("리퀘스트 값 : " + request.getParameter("id"));
		try {
			String paramId = (request.getParameter("id") == null) ? "" : String.valueOf(request.getParameter("id"));
			Member m = new Member();
			m.setId(paramId.trim());
			System.out.println("member 아이디 값 : " + m.getId());
			Member member = memberService.dupMember(m);
			if (member == null)
				chk = 1;
			out.print(chk);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.print(0);
		}
	}

	// 회원가입 이메일 인증
	@RequestMapping(value = "sendMail.do", method = RequestMethod.POST)
	public void sendMailAuth(Member m, HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		PrintWriter out = response.getWriter();
		System.out.println("이메일 인증 컨트롤러.....");
		String email = request.getParameter("email");
		m.setEmail(email);
		Member member = memberService.emailCheck(m);

		if (member != null) {
			System.out.println("이메일 중복");
			out.print("emailDup");
			out.flush();
			out.close();
			return;
		}
		System.out.println(email);
		if (!email.contains("@")) {
			out.print("fail");
			return;
		}

		int ran = new Random().nextInt(100000) + 10000; // 10000 ~ 99999
		String joinCode = String.valueOf(ran);

		String subject = "회원가입 인증 코드 발급 안내 입니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("귀하의 인증 코드는 " + joinCode + " 입니다.");
		boolean flag = memberService.send(subject, sb.toString(), "wkdgma91@gmail.com", email, null);
		System.out.println("flag 값 확인 : " + flag);
		if (flag) {
			out.print(joinCode);
		} else {
			out.print("fail");
		}
		out.flush();
		out.close();
	}

	@RequestMapping(value = "idSearch.do", method = RequestMethod.POST)
	public void sendMailId(Member m, HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		PrintWriter out = response.getWriter();
		System.out.println("이메일 인증(아이디) 컨트롤러.....");
		String email = request.getParameter("email");
		if (!email.contains("@")) {
			out.print(0); // 유효하지 않은 이메일
			out.flush();
			out.close();
			return;
		}

		m.setEmail(email);
		Member member = memberService.emailCheck(m);
		if (member == null) {
			out.print(1); // 등록되지 않은 이메일
			out.flush();
			out.close();
			return;
		}
		System.out.println("아이디 찾기를 위한 이메일 : " + member.getEmail());

		String subject = "hmm 아이디 안내 입니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("귀하의 아이디는 " + member.getId() + " 입니다.");
		boolean flag = memberService.send(subject, sb.toString(), "wkdgma91@gmail.com", email, null);
		if (!flag) {
			out.print(2); // 이메일 발송 실패
			out.flush();
			out.close();
		} else {
			out.print(3);
			out.flush();
			out.close();
		}
		return;
	}

	@RequestMapping(value = "pwdSearch.do", method = RequestMethod.POST)
	public void sendMailPwd(Member m, HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		PrintWriter out = response.getWriter();
		System.out.println("이메일 인증(패스워드) 컨트롤러.....");
		int ran = new Random().nextInt(100000) + 10000; // 10000 ~ 99999
		String joinCode = String.valueOf(ran);
		m.setId(request.getParameter("id"));
		m.setEmail(request.getParameter("email"));
		m.setPassword(joinCode);
		if (!m.getEmail().contains("@")) {
			out.print(0); // 유효하지 않은 이메일
			out.flush();
			out.close();
			return;
		}

		Member member = memberService.checkEmailId(m);
		if (member == null) {
			out.print(1); // 등록되지 않은 멤버
			out.flush();
			out.close();
			return;
		}
	
		System.out.println("패스워드 찾기를 위한 이메일 : " + member.getEmail());
		String subject = "hmm 패스워드 안내 입니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("귀하의 임시 패스워드는 " + joinCode + " 입니다.");
		boolean flag = memberService.send(subject, sb.toString(), "wkdgma91@gmail.com", member.getEmail(), null);
		if (!flag) {
			out.print(2); // 이메일 발송 실패
			out.flush();
			out.close();
		} else {
			out.print(3);
			out.flush();
			out.close();
		}
		return;
	}

	@ResponseBody
	@RequestMapping(value = "leveling.do", method = RequestMethod.POST)
	public HashMap<String, Integer> leveling(HttpServletResponse response, long exp) throws Exception {
		logger.info("leveling() call...");

		System.out.println(exp);
		
		int lev = memberService.leveling(exp);

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		if (exp > 0) {
			map.put("level", lev);
		} else {
			map.put("level", 1);
		}

		return map;
	}

	@ResponseBody
	@RequestMapping(value = "recompoint.do", method = RequestMethod.POST)
	public Integer recompoint(HttpServletResponse response, String id) throws Exception {
		logger.info("recompoint() call...");

		Integer result = memberService.recompoint(id);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "havmedal.do", method = RequestMethod.POST)
	public Integer havmedal(HttpServletResponse response, int membercode) throws Exception {
		logger.info("havmedal() call...");

		Integer result = memberService.havmedal(membercode);

		return result;
	}

	@RequestMapping("profile.do")
	public String profileMember(HttpSession session, HttpServletRequest request, Model model, String profileId) {
		logger.info("profileMember() call...");
		Member m = memberService.profileInfo(profileId);
		if (m != null) {
			model.addAttribute("pInfo", m);
		}

		return "member/profile";
	}
	
	@RequestMapping("profileHavMedal.do")
	public void profileHavMedalMember(HttpSession session, HttpServletRequest request, HttpServletResponse response,Model model, String profileId) throws IOException {
		logger.info("profileMember() call...");
		PrintWriter out = response.getWriter();
		Member m = memberService.profileInfo(profileId);

		if (m != null) {
			out.print(m.getMedal()); // 유효하지 않은 이메일
			out.flush();
			out.close();
		}
	}

	@RequestMapping(value = "buyDDaru.do", method = RequestMethod.GET)
	public String buyDDaru(HttpSession session,HttpServletRequest request, int membercode, int price) throws Exception
	{
		logger.info("buyDDaru() call...");
		
		System.out.println("request membercode 값 : " + request.getParameter("membercode"));
		System.out.println("request price 값 : " + request.getParameter("price"));
		Member m = new Member();
		m.setDdaru(Integer.parseInt(request.getParameter("price")));
		m.setMembercode(Integer.parseInt(request.getParameter("membercode")));
		Member member = memberService.buyDDARU(m);
		
		if(member != null){
			session.setAttribute("member",member);
		}else{
			session.setAttribute("member", null);
		}
				
		return "forward:/itemLists.do";
		
	}
}

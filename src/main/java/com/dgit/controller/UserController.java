package com.dgit.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.MemberVO;
import com.dgit.service.MemberService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = 
			LoggerFactory.getLogger(UserController.class);

	@Autowired
	private MemberService service;
	
	// /user/login
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void getLogin(){
		
	}
	@RequestMapping(value="/loginPost", method=RequestMethod.POST)
	public void postLogin(MemberVO member, Model model) throws Exception{
		MemberVO vo = service.login(member.getUserid(), member.getUserpw());
		System.out.println(vo);
		
		if(vo == null){
			// 회원가입을 한 적이 없으면, memberVO키가 없음
			// interceptor에서 memberVO키가 없으면 login화면으로 다시 가도록 처리
			System.out.println("회원없음.");
			return;
		}
		model.addAttribute("loginVO", vo);
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout(HttpSession session){
		session.removeAttribute("login");
		session.invalidate();
	}
	
}

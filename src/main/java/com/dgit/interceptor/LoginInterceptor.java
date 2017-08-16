package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dgit.domain.MemberVO;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final String LOGIN = "login";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Login Interceptor PreHandle ---------@");
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("Login Interceptor PostHandle ---------@");
		MemberVO loginVO = (MemberVO) modelAndView.getModel().get("loginVO");
		
		if(loginVO == null){
			response.sendRedirect("login");// 회원가입으로 유도해야하나, 화면X 이리 처리함.
		}else{
			//로그인 처리시 session영역에 login한 사람의 정보 넣음
			HttpSession session = request.getSession();
			session.setAttribute(LOGIN, loginVO.getUserid());
			
			//login이전에 이동될 uri가 있다면 dest에 저장을 해두었음.
			//저장된 dest의 값을 받아, 그곳으로 이동되도록 함.
			String path = (String) session.getAttribute("dest");
			if(path != null){
				response.sendRedirect(path);
			}
		}
	}
	
}

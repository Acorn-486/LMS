package com.sample.lms.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.lms.member.model.MemberInput;
import com.sample.lms.member.model.ResetPasswordInput;
import com.sample.lms.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;
	
	@RequestMapping("/member/login")
	public String login() {
		
		return "member/login";
	}
	
	@GetMapping("/member/find/password")
	public String findPassword() {
		
		return "member/find_password";
	}
	
	@PostMapping("/member/find/password")
	public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {
		
		boolean result = false;
		
		try {
			result = memberService.sendResetPassword(parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("result", result);
		
		return "member/find_password_result";
	}
	
	@GetMapping("/member/register")
	public String register() {
		
		return "member/register";
	}
	
	@PostMapping("/member/register")
	public String registerSubmit(Model model, HttpServletRequest request, HttpServletResponse response,
			MemberInput parameter) {
		
		boolean result = memberService.register(parameter);
		
		model.addAttribute("result", result);
		
		return "member/register_complete";
	}
	
	@GetMapping("/member/email-auth")
	public String emailAuth(Model model, HttpServletRequest request) {
		
		String uuid = request.getParameter("id");
		
		boolean result = memberService.emailAuth(uuid);
		model.addAttribute("result", result);
		
		return "member/email_auth";
		
	}
	
	@GetMapping("/member/info")
	public String memberInfo() {
		
		return "member/info";
	}
	
	@GetMapping("/member/reset/password")
	public String resetPassword() {
		
		
		
		return "member/reset_password";
	}
}

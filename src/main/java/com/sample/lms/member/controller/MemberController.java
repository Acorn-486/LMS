package com.sample.lms.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sample.lms.member.model.MemberInput;
import com.sample.lms.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;
	
	@GetMapping("/member/register")
	public String register() {
		
		return "member/register";
	}
	
	@PostMapping("/member/register")
	public String registerSubmit(HttpServletRequest request, HttpServletResponse response,
			MemberInput parameter) {
		
		boolean result = memberService.register(parameter);
		
		return "member/register_complete";
	}
}

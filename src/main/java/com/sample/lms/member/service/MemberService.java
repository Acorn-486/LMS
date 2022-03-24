package com.sample.lms.member.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sample.lms.member.model.MemberInput;

public interface MemberService extends UserDetailsService {

	boolean register(MemberInput parameter);
	
	boolean emailAuth(String uuid);
	
}

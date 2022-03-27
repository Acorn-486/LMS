package com.sample.lms.member.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sample.lms.member.model.MemberInput;
import com.sample.lms.member.model.ResetPasswordInput;

public interface MemberService extends UserDetailsService {

	boolean register(MemberInput parameter);
	
	boolean emailAuth(String uuid);

	boolean sendResetPassword(ResetPasswordInput parameter);
	
}

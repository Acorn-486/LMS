package com.sample.lms.member.service;

import com.sample.lms.member.model.MemberInput;

public interface MemberService {

	boolean register(MemberInput parameter);
	
	boolean emailAuth(String uuid);
	
}

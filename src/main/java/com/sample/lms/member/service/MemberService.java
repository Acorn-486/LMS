package com.sample.lms.member.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sample.lms.member.model.MemberInput;
import com.sample.lms.member.model.ResetPasswordInput;

public interface MemberService extends UserDetailsService {

	boolean register(MemberInput parameter);
	
	/*
	 * uuid에 해당하는 계정을 활성화
	 */
	boolean emailAuth(String uuid);

	/*
	 * 입력한 이메일로 비밀번호 재설정 정보 전송
	 */
	boolean sendResetPassword(ResetPasswordInput parameter);

	/*
	 * 입력받은 uuid에 대해서 password로 재설정
	 */
	boolean resetPassword(String uuid, String password);

	/*
	 * 입력받은 uuid값이 유효한지 확인
	 */
	boolean checkResetPassword(String uuid);
	
}

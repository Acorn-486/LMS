package com.sample.lms.member.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.sample.lms.components.MailComponents;
import com.sample.lms.member.entity.Member;
import com.sample.lms.member.exception.MemberNotEmailAuthException;
import com.sample.lms.member.model.MemberInput;
import com.sample.lms.member.model.ResetPasswordInput;
import com.sample.lms.member.repository.MemberRepository;
import com.sample.lms.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final MailComponents mailComponents;
	
	@Override
	public boolean register(MemberInput parameter) {
		
		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserID());
		if (optionalMember.isPresent()) {
			return false;
		}
		
		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
		String uuid = UUID.randomUUID().toString();
		
		Member member = Member.builder()
				.userId(parameter.getUserID())
				.userName(parameter.getUserName())
				.phone(parameter.getPhone())
				.password(encPassword)
				.regDt(LocalDateTime.now())
				.emailAuthYn(false)
				.emailAuthKey(uuid)
				.build();
		
		memberRepository.save(member);
		
		String email = parameter.getUserID();
		String subject = "LMS 사이트 가입을 축하드립니다.";
		String text = "<p>LMS 사이트 가입을 축하드립니다.</p><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
				+ "<div><a target='_blank' href='http://localhost:8080/member/email-auth?id=" + uuid + "'>가입 완료</a></div>";
		
		mailComponents.sendMail(email, subject, text);
		
		return true;
	}

	@Override
	public boolean emailAuth(String uuid) {
		
		Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
		if (!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		memberRepository.save(member);
		
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> optionalMember = memberRepository.findById(username);
		if (!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		
		Member member = optionalMember.get();
		if (!member.isEmailAuthYn()) {
			throw new MemberNotEmailAuthException("이메일 인증 이후 로그인을 해주세요.");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
	}

	@Override
	public boolean sendResetPassword(ResetPasswordInput parameter) {
		
		Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserID(), parameter.getUserName());
		if (!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		
		Member member = optionalMember.get();
		
		String uuid = UUID.randomUUID().toString();
		
		member.setResetPasswordKey(uuid);
		member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
		memberRepository.save(member);
		
		String email = parameter.getUserID();
		String subject = "[LMS] 비밀번호 초기화 메일 입니다";
		String text = "<p>LMS 비밀번호 초기화 메일 입니다.</p>"
				+ "<p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>"
				+ "<div><a target='_blank' href='http://localhost:8080/member/reset/password?id=" + uuid + "'>비밀번호 초기화 링크</a></div>";
		
		mailComponents.sendMail(email, subject, text);
		
		return true;
	}

}

package com.sample.lms.member.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sample.lms.components.MailComponents;
import com.sample.lms.member.entity.Member;
import com.sample.lms.member.model.MemberInput;
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
		
		String uuid = UUID.randomUUID().toString();
		
		Member member = Member.builder()
				.userId(parameter.getUserID())
				.userName(parameter.getUserName())
				.phone(parameter.getPhone())
				.password(parameter.getPassword())
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

}
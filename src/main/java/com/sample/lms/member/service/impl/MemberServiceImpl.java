package com.sample.lms.member.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sample.lms.member.entity.Member;
import com.sample.lms.member.model.MemberInput;
import com.sample.lms.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	
	@Override
	public boolean register(MemberInput parameter) {
		
		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserID());
		if (optionalMember.isPresent()) {
			return false;
		}
		
		Member member = new Member();
		member.setUserId(parameter.getUserID());
		member.setUserName(parameter.getUserName());
		member.setPhone(parameter.getPhone());
		member.setPassword(parameter.getPassword());
		member.setRegDt(LocalDateTime.now());
		
		memberRepository.save(member);
		
		return false;
	}

}

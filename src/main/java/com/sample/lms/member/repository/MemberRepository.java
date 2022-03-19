package com.sample.lms.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sample.lms.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
}

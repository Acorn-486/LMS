package com.sample.lms.member.model;

import lombok.Data;

@Data
public class MemberInput {

	private String userID;
	private String userName;
	private String password;
	private String phone;
}

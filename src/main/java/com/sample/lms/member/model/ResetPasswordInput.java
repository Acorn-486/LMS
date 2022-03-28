package com.sample.lms.member.model;

import lombok.Data;

@Data
public class ResetPasswordInput {

	private String userID;
	private String userName;
}

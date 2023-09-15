package com.tencoding.bank.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignInFormDto {
	private String username;
	private String password;
}

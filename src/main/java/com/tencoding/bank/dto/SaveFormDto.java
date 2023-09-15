package com.tencoding.bank.dto;

import lombok.Data;

@Data
public class SaveFormDto {
	public String number;
	public String password;
	public Long balance;
}

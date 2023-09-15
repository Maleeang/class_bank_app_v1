package com.tencoding.bank.dto;

import lombok.Data;

@Data
public class WithdrawFromDto {
	public Long amount;
	public String wAccountNumber;
	public String wAccountPassword;
}

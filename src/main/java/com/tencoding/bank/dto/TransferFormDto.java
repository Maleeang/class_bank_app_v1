package com.tencoding.bank.dto;

import lombok.Data;

@Data
public class TransferFormDto {
	public Long amount;
	public String wAccountNumber;
	public String dAccountNumber;
	public String wAccountPassword;
}

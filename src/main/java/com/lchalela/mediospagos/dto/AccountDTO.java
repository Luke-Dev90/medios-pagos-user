package com.lchalela.mediospagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Transient;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
	private BigDecimal balance;
	private String typeAccount;
	private String accountNumber;
	private String cbu;
	private String alias;
	private Long userId;
	private Boolean isActived;
	@Transient
	private List<TransactionDTO> transactionDTO;
}

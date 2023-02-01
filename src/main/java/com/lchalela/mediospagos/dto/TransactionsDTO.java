package com.lchalela.mediospagos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsDTO {
	private String accountDestination;
	private String accountOrigin;
	private String reason;
	private LocalDateTime dateSent;
	private BigDecimal amount;
}

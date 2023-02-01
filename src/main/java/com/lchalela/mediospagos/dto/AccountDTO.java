package com.lchalela.mediospagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    private List<TransactionsDTO> transactionsDTO;
}

package com.lchalela.mediospagos.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String typeAccount;
    private String cbu;
    private String alias;
    private List<TransactionsDTO> transactionsDTO;
}

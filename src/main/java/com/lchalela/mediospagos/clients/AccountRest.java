package com.lchalela.mediospagos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lchalela.mediospagos.dto.AccountCreateDTO;
import com.lchalela.mediospagos.dto.AccountDTO;

@FeignClient(name = "account-service")
public interface AccountRest {
	@PostMapping("/v1/create")
	public List<AccountDTO> createAccount(@RequestBody AccountCreateDTO accountCreateDTO);
	
	@GetMapping("/v1/{id}")
	public List<AccountDTO> getAccountByUserId(@PathVariable Long id);
}

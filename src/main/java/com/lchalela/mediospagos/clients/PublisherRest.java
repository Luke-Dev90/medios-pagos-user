package com.lchalela.mediospagos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-publisher")
public interface PublisherRest {
	@PostMapping("/v1/send-email")
	public ResponseEntity<String> sendEmail(@RequestBody String message);
}

package clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "account-service")
public interface AccountRest {
	
}

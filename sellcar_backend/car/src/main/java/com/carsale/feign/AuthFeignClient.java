package com.carsale.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="auth")
//public interface AuthFeignClient {
//	@GetMapping("/{username}/role")
//    public ResponseEntity<String> getUserRole(@PathVariable String username);
//}

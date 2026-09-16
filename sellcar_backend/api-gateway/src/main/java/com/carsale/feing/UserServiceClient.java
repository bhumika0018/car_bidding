package com.carsale.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth",url = "http://localhost:8081")
public interface UserServiceClient {

    @PostMapping("/auth")
    void validateToken(@RequestParam String token);

}
package com.atamertc.manager;

import com.atamertc.dto.request.CreateUserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-manager", url = "${feign.url}/api/v1/user", decode404 = true)
public interface UserManager {

    @PostMapping("/create-user")
    ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDto dto, @RequestHeader(value = "Authorization") String token);

    @PostMapping("/activate-user")
    ResponseEntity<String> activateUser(@RequestParam Long authid);

}

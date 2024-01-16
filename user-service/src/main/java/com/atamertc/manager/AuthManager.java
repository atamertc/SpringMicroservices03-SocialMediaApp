package com.atamertc.manager;

import com.atamertc.dto.request.UpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "auth-manager", url = "${feign.url}/api/v1/auth", decode404 = true)
public interface AuthManager {

    @PutMapping("/update-auth")
    ResponseEntity<Boolean> updateAuth(@RequestBody UpdateRequestDto dto, @RequestHeader(value = "Authorization") String token);

}

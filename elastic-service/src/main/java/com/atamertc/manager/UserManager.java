package com.atamertc.manager;

import com.atamertc.dto.response.UserResponseDto;
import com.atamertc.repository.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-manager", url = "http://localhost:9091/api/v1/user", decode404 = true)
public interface UserManager {

    @GetMapping("/find-all-user-elastic")
    ResponseEntity<List<UserResponseDto>> findAllUser2();

}

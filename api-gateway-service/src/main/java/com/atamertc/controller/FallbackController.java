package com.atamertc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/fallback")
public class FallbackController {


    @GetMapping("/authservice")
    public ResponseEntity<String> authServiceFallback(){
        return  ResponseEntity.ok("Auth service suanda hizmet verememektedir!!!");
    }

    @GetMapping("/userservice")
    public ResponseEntity<String> userServiceFallback(){
        return  ResponseEntity.ok("User service suanda hizmet verememektedir!!!");
    }

}

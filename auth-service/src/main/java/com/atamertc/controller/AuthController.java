package com.atamertc.controller;

import com.atamertc.dto.request.ActivationRequestDto;
import com.atamertc.dto.request.LoginRequestDto;
import com.atamertc.dto.request.RegisterRequestDto;
import com.atamertc.dto.request.UpdateRequestDto;
import com.atamertc.dto.response.LoginResponseDto;
import com.atamertc.dto.response.RegisterResponseDto;
import com.atamertc.repository.entity.Auth;
import com.atamertc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(service.register(dto));
    }

    @PostMapping("/register-with-rabbitmq")
    public ResponseEntity<RegisterResponseDto> registerWithRabbitmq(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(service.registerWithRabbitmq(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping("/activation")
    public ResponseEntity<String> activateStatus(@RequestBody ActivationRequestDto dto) {
        return ResponseEntity.ok(service.activateStatus(dto));
    }

    @DeleteMapping("/delete-user-by-id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteUserById(id));
    }

    @PutMapping("/update-auth")
    public ResponseEntity<Boolean> updateAuth(@RequestBody UpdateRequestDto dto, @RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(service.updateAuth(dto, token));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<Auth>> findAllAuth() {
        return ResponseEntity.ok(service.findAll());
    }




}

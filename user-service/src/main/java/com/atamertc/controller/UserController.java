package com.atamertc.controller;

import com.atamertc.constants.EndPoints;
import com.atamertc.dto.request.CreateUserRequestDto;
import com.atamertc.dto.request.UpdateRequestDto;
import com.atamertc.dto.response.UserResponseDto;
import com.atamertc.repository.entity.User;
import com.atamertc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EndPoints.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/create-user")
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDto dto, @RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(service.createUser(dto));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<User>> findAllUser() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find-all-user-elastic")
    public ResponseEntity<List<UserResponseDto>> findAllUser2() {
        return ResponseEntity.ok(service.findAllForElastic());
    }

    @PutMapping("/update-user")
    public ResponseEntity<Boolean> updateUser(@RequestBody UpdateRequestDto dto, @RequestParam String token) {
        return ResponseEntity.ok(service.updateUser(dto, token));
    }

    @PostMapping("/find-by-username")
    public ResponseEntity<User> findUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(service.findUserByUsername(username));
    }

    @PostMapping("/find-by-status")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<User>> findUserByStatus(@RequestParam String status) {
        return ResponseEntity.ok(service.findUserByStatus(status));
    }

    @GetMapping("/get-user")
    public ResponseEntity<Optional<User>> getUser(String username) {
        return ResponseEntity.ok(service.getUser(username));
    }
}

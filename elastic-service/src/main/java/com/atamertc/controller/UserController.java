package com.atamertc.controller;

import com.atamertc.constants.EndPoints;
import com.atamertc.dto.request.PagingRequestDto;
import com.atamertc.repository.entity.User;
import com.atamertc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoints.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/find-all")
    public ResponseEntity<Iterable<User>> findAllUser() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find-all-by-pageable")
    public ResponseEntity<Page<User>> findAllByPageable(@RequestBody PagingRequestDto dto) {
        return ResponseEntity.ok(service.findAllByPageable(dto));
    }

    @GetMapping("/find-all-by-slice")
    public ResponseEntity<Slice<User>> findAllBySlice(int pageSize, int pageNumber, @RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(required = false, defaultValue = "id") String sortParameter) {
        return ResponseEntity.ok(service.findAllSlice(pageSize,pageNumber,direction,sortParameter));
    }




}

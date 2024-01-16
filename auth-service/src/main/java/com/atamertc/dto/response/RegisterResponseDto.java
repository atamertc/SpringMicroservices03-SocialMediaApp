package com.atamertc.dto.response;

import com.atamertc.repository.enums.ERole;
import com.atamertc.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDto {
    private Long id;
    private String username;
    private String email;
    private String activationCode;
    private ERole role;
    private EStatus status;
}

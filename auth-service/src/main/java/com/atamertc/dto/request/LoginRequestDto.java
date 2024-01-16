package com.atamertc.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto {
    @NotBlank(message = "Kullanici adi bos gecilemez.")
    @Size(min = 4, max = 20, message = "Kullanici adi en az 4 karakter en fazla 20 karakter olabilir.")
    private String username;
    @NotBlank(message = "Sifre bos gecilemez.")
    @Size(min = 5, max = 32, message = "Sifre en az 4 karakter en fazla 20 karakter olabilir.")
    private String password;
}

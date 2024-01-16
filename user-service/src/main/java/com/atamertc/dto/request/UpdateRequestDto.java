package com.atamertc.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRequestDto {
    @NotBlank(message = "Kullanici adi bos gecilemez.")
    @Size(min = 4, max = 20, message = "Kullanici adi en az 4 karakter en fazla 20 karakter olabilir.")
    private String username;
    @Email(message = "Lutfen gecerli bir e-mail formati giriniz.")
    private String email;
    private String phone;
    private String avatar;
    private String address;
    private String about;
}

package com.atamertc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorType {

    USERNAME_EXIST(4210, "Kullanici zaten mevcut", HttpStatus.BAD_REQUEST),
    USER_NOT_CREATED(4211, "Kullanici olusturulamadi", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4212, "Boyle bir kullanici bulunamadi", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXIST(4212, "Bu kullanici sistemde kayitli.", HttpStatus.BAD_REQUEST),
    USER_NOT_UPDATED(4212, "Kullanici guncelleme islemi basarisiz oldu.", HttpStatus.BAD_REQUEST),
    USERNAME_OR_PASSWORD_INCORRECT(1001, "Kullanici adi veya sifre hatali.", HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_MISMATCH(4106, "Aktivasyon kodunuz uyusmadi.", HttpStatus.NOT_FOUND),
    INVALID_TOKEN(4216, "Gecersiz token", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(4217, "Token olusturulamadi", HttpStatus.BAD_REQUEST),
    PERSONAL_EMAIL_IS_TAKEN(4218, "Bu email sistemde kayitli", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_PERMISSION(4219, "Bu islemi yapmaya yetkiniz yok", HttpStatus.BAD_REQUEST),
    USER_TYPE_MISMATCH(12324, "Kullanicinin tipi bu istege uygun degildir.", HttpStatus.BAD_REQUEST),


    INTERNAL_ERROR_SERVER(5200, "Sunucu Hatasi", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4200, "Parametre hatasi", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus status;
}

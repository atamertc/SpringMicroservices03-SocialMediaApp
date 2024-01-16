package com.atamertc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    //Genel Hatalar
    BAD_REQUEST(4100, "Parametre Hatasi", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(4101, "Token olusturulamadi.", HttpStatus.BAD_REQUEST),

    //AuthService Hatalari
    USERNAME_EXIST(4101, "Bu kullanici adina sahip bir kullanici mevcut.", HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4102, "Kullanici adi veya sifre hatali", HttpStatus.UNAUTHORIZED),
    USER_NOT_CREATED(4103, "Kullanici kaydi yapilamadi.", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE(4104, "Aktive edilmemis hesap.", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(4105, "Boyle bir kullanici bulunamadi", HttpStatus.NOT_FOUND),
    ACTIVATION_CODE_MISMATCH(4106, "Aktivasyon kodunuz uyusmadi.", HttpStatus.NOT_FOUND),
    USER_ALREADY_DELETED(4107, "Bu kullanici zaten silinmis.", HttpStatus.BAD_REQUEST),
    AUTH_NOT_UPDATED(4108, "Kullanici kaydi yapilamadi.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4109, "Gecersiz token.", HttpStatus.BAD_REQUEST),

    //UserService Hatalari
    MOVIE_NOT_FOUND(4201, "Boyle bir film bulunamadi", HttpStatus.NOT_FOUND),

    //CommentService Hatalari
    COMMENT_NOT_FOUND(4301, "Boyle bir yorum bulunamadi", HttpStatus.NOT_FOUND),



    INTERNAL_ERROR_SERVER(5100, "Sunucu Hatasi", HttpStatus.INTERNAL_SERVER_ERROR);

    private int code;
    private String message;
    HttpStatus httpStatus;
}

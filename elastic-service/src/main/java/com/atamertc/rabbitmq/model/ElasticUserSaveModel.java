package com.atamertc.rabbitmq.model;

import com.atamertc.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElasticUserSaveModel implements Serializable {
    private String userid;
    private Long authid;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String address;
    private String about;
    private EStatus status;
}

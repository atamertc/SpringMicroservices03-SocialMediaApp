package com.atamertc.mapper;

import com.atamertc.dto.request.CreateUserRequestDto;
import com.atamertc.dto.request.RegisterRequestDto;
import com.atamertc.dto.response.LoginResponseDto;
import com.atamertc.dto.response.RegisterResponseDto;
import com.atamertc.rabbitmq.model.ActivateUserModel;
import com.atamertc.rabbitmq.model.MailSendModel;
import com.atamertc.rabbitmq.model.RegisterUserModel;
import com.atamertc.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    Auth toAuth(final RegisterRequestDto dto);

    RegisterResponseDto toRegisterResponseDto(final Auth auth);

    @Mapping(target = "authid", source = "id")
    CreateUserRequestDto toCreateUserRequestDto(final Auth auth);

    @Mapping(target = "authid", source = "id")
    RegisterUserModel toRegisterUserModel(final Auth auth);

    @Mapping(target = "authid", source = "id")
    ActivateUserModel toActivateUserModel(final Auth auth);

    MailSendModel toMailSendModel(final Auth auth);

}

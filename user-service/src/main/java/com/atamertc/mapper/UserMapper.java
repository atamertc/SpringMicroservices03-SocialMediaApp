package com.atamertc.mapper;


import com.atamertc.dto.request.CreateUserRequestDto;
import com.atamertc.dto.request.UpdateRequestDto;
import com.atamertc.dto.response.UserResponseDto;
import com.atamertc.rabbitmq.model.ElasticUserSaveModel;
import com.atamertc.rabbitmq.model.RegisterUserModel;
import com.atamertc.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(final CreateUserRequestDto dto);

    User toUser(final RegisterUserModel model);

    @Mapping(target = "userid", source = "id")
    UserResponseDto toUserResponseDto(final User user);

    @Mapping(target = "userid", source = "id")
    ElasticUserSaveModel toElasticUserSaveModel(final User user);
}

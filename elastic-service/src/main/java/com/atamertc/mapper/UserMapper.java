package com.atamertc.mapper;


import com.atamertc.dto.response.UserResponseDto;
import com.atamertc.graphql.model.UserInput;
import com.atamertc.rabbitmq.model.ElasticUserSaveModel;
import com.atamertc.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(final UserResponseDto dto);

    User toUser(final ElasticUserSaveModel model);

    User toUser(final UserInput userInput);


}

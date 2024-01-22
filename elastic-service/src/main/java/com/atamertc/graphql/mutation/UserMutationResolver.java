package com.atamertc.graphql.mutation;

import com.atamertc.exception.ElasticManagerException;
import com.atamertc.exception.ErrorType;
import com.atamertc.graphql.model.UserInput;
import com.atamertc.mapper.UserMapper;
import com.atamertc.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMutationResolver implements GraphQLMutationResolver {
    private final UserService service;

    public Boolean createUser(UserInput userInput) {
        try {
            service.save(UserMapper.INSTANCE.toUser(userInput));
            return true;
        } catch (Exception e) {
            throw new ElasticManagerException(ErrorType.USER_NOT_CREATED);
        }
    }
}

package com.atamertc.graphql.query;

import com.atamertc.repository.entity.User;
import com.atamertc.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserQueryResolver implements GraphQLQueryResolver {
    private final UserService service;

    public Iterable<User> findAll() {
        return service.findAll();
    }

    public List<User> findAllContainingEmail(String value) {
        return service.findAllContainingEmail(value);
    }

    public List<User> findAllByStatus(String status) {
        return service.findAllByStatus(status);
    }

    public List<User> findAllByStatusOrAddress(String status, String address) {
        return service.findAllByStatusOrAddress(status, address);
    }

    public User findByUsername(String username) {
        return service.findByUsername(username);
    }
}

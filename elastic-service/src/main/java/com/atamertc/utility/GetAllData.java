package com.atamertc.utility;

import com.atamertc.repository.entity.User;
import com.atamertc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllData {
    private final UserService userService;

//    @PostConstruct
    public void init() {
        userService.findAllUserForElasticAndSave();
    }

}

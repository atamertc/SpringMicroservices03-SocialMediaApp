package com.atamertc.service;

import com.atamertc.dto.request.CreateUserRequestDto;
import com.atamertc.dto.request.UpdateRequestDto;
import com.atamertc.dto.response.UserResponseDto;
import com.atamertc.exception.ErrorType;
import com.atamertc.exception.UserManagerException;
import com.atamertc.manager.AuthManager;
import com.atamertc.mapper.UserMapper;
import com.atamertc.rabbitmq.model.ActivateUserModel;
import com.atamertc.rabbitmq.model.RegisterUserModel;
import com.atamertc.rabbitmq.producer.ElasticSaveProducer;
import com.atamertc.repository.UserRepository;
import com.atamertc.repository.entity.User;
import com.atamertc.repository.enums.EStatus;
import com.atamertc.utility.JwtTokenManager;
import com.atamertc.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService extends ServiceManager<User, String> {
    private final UserRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthManager authManager;
    private final CacheManager cacheManager;
    private final ElasticSaveProducer elasticSaveProducer;

    public UserService(UserRepository repository, JwtTokenManager jwtTokenManager, AuthManager authManager, CacheManager cacheManager, ElasticSaveProducer elasticSaveProducer) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
        this.cacheManager = cacheManager;
        this.elasticSaveProducer = elasticSaveProducer;
    }

    public Boolean createUser(CreateUserRequestDto dto) {
        if (repository.existsByAuthid(dto.getAuthid())) {
            throw new UserManagerException(ErrorType.USER_ALREADY_EXIST);
        }
        User user = UserMapper.INSTANCE.toUser(dto);
        save(user);
        elasticSaveProducer.sendElasticSaveMessage(UserMapper.INSTANCE.toElasticUserSaveModel(user));
        return true;
    }

    public void rabbitRegisterUser(RegisterUserModel model) {
        if (repository.existsByAuthid(model.getAuthid())) {
            throw new UserManagerException(ErrorType.USER_ALREADY_EXIST);
        }
        User user = UserMapper.INSTANCE.toUser(model);
        try {
            cacheManager.getCache("findByStatus").evict(user.getStatus());
            save(user);
            elasticSaveProducer.sendElasticSaveMessage(UserMapper.INSTANCE.toElasticUserSaveModel(user));
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    public void rabbitActivateUser(ActivateUserModel model) {
        Optional<User> user = repository.findByAuthid(model.getAuthid());
        if (user.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        user.get().setStatus(EStatus.ACTIVE);
        update(user.get());
        cacheManager.getCache("findByStatus").evict(user.get().getStatus());
    }

    @Transactional
    public Boolean updateUser(UpdateRequestDto dto, String token) {
        Optional<Long> authid = jwtTokenManager.getIdFromToken(token);
        Optional<User> user = repository.findByAuthid(authid.get());
        if (user.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        try {
            user.get().setUsername(dto.getUsername());
            user.get().setEmail(dto.getEmail());
            user.get().setAvatar(dto.getAvatar());
            user.get().setPhone(dto.getPhone());
            user.get().setAddress(dto.getAddress());
            user.get().setAbout(dto.getAbout());
            update(user.get());
            authManager.updateAuth(dto, "Bearer " + token);
            return true;
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.USER_NOT_UPDATED);
        }
    }

    @Cacheable(value = "findByUsername", key = "#username.toLowerCase()")
    public User findUserByUsername(String username) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Optional<User> user = repository.findByUsername(username.toLowerCase());
        if (user.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        return user.get();
    }

    @Cacheable(value = "findByStatus", key = "#status.toUpperCase()")
    public List<User> findUserByStatus(String status) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Optional<List<User>> user = repository.findByStatus(EStatus.valueOf(status.toUpperCase(Locale.ENGLISH)));
        if (user.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        return user.get();
    }


    public List<UserResponseDto> findAllForElastic() {
        List<UserResponseDto> dtos = new ArrayList<>();
        findAll().forEach(x -> {
            dtos.add(UserMapper.INSTANCE.toUserResponseDto(x));
        });
        return dtos;
    }

    public Optional<User> getUser(String username) {
        return repository.getUser(username);
    }


}

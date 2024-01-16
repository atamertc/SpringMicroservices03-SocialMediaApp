package com.atamertc.service;

import com.atamertc.dto.request.ActivationRequestDto;
import com.atamertc.dto.request.LoginRequestDto;
import com.atamertc.dto.request.RegisterRequestDto;
import com.atamertc.dto.request.UpdateRequestDto;
import com.atamertc.dto.response.LoginResponseDto;
import com.atamertc.dto.response.RegisterResponseDto;
import com.atamertc.exception.AuthManagerException;
import com.atamertc.exception.ErrorType;
import com.atamertc.manager.UserManager;
import com.atamertc.mapper.AuthMapper;
import com.atamertc.rabbitmq.producer.ActivateUserProducer;
import com.atamertc.rabbitmq.producer.MailSendProducer;
import com.atamertc.rabbitmq.producer.RegisterUserProducer;
import com.atamertc.repository.AuthRepository;
import com.atamertc.repository.entity.Auth;

import com.atamertc.repository.enums.EStatus;
import com.atamertc.utility.CodeGenerator;
import com.atamertc.utility.JwtTokenManager;
import com.atamertc.utility.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final AuthRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final UserManager userManager;
    private final RegisterUserProducer registerUserProducer;
    private final ActivateUserProducer activateUserProducer;
    private final MailSendProducer mailSendProducer;
    private final PasswordEncoder passwordEncoder;


    public AuthService(AuthRepository repository, JwtTokenManager jwtTokenManager, UserManager userManager, RegisterUserProducer registerUserProducer, ActivateUserProducer activateUserProducer, MailSendProducer mailSendProducer, PasswordEncoder passwordEncoder) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.userManager = userManager;
        this.registerUserProducer = registerUserProducer;
        this.activateUserProducer = activateUserProducer;
        this.mailSendProducer = mailSendProducer;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new AuthManagerException(ErrorType.USERNAME_EXIST);
        }
        try {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            Auth auth = AuthMapper.INSTANCE.toAuth(dto);
            auth.setActivationCode(CodeGenerator.genarateCode());
            save(auth);
            String token = "Bearer " + jwtTokenManager.createToken(auth.getId(), auth.getRole()).get();
            userManager.createUser(AuthMapper.INSTANCE.toCreateUserRequestDto(auth), token);
            mailSendProducer.sendMailActivation(AuthMapper.INSTANCE.toMailSendModel(auth));
            return AuthMapper.INSTANCE.toRegisterResponseDto(auth);
        } catch (Exception e) {
            throw new AuthManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    public RegisterResponseDto registerWithRabbitmq(RegisterRequestDto dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new AuthManagerException(ErrorType.USERNAME_EXIST);
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Auth auth = AuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.genarateCode());
        save(auth);
        registerUserProducer.sendRegisterUserMessage(AuthMapper.INSTANCE.toRegisterUserModel(auth));
        mailSendProducer.sendMailActivation(AuthMapper.INSTANCE.toMailSendModel(auth));
        return AuthMapper.INSTANCE.toRegisterResponseDto(auth);
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<Auth> auth = repository.findByUsername(dto.getUsername());
        if (auth.isEmpty() || !passwordEncoder.matches(dto.getPassword(), auth.get().getPassword())) {
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        }
        if (!auth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
        Optional<String> token = jwtTokenManager.createToken(auth.get().getId(), auth.get().getRole());
        if (token.isEmpty()) {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        return new LoginResponseDto(token.get());
    }

    public String activateStatus(ActivationRequestDto dto) {
        Optional<Auth> auth = findById(dto.getId());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!auth.get().getActivationCode().equals(dto.getActivationCode())) {
            throw new AuthManagerException(ErrorType.ACTIVATION_CODE_MISMATCH);
        }
        return statusControl(auth.get());
    }

    public String statusControl(Auth auth) {
        switch (auth.getStatus()) {
            case ACTIVE -> {
                return "Hesabiniz zaten aktif.";
            }
            case BANNED -> {
                return "Hesabiniz yasaklanmistir.";
            }
            case DELETED -> {
                return "Bu hesap silinmistir.";
            }
            case PENDING, INACTIVE -> {
                auth.setStatus(EStatus.ACTIVE);
                update(auth);
                activateUserProducer.sendActivateUser(AuthMapper.INSTANCE.toActivateUserModel(auth));
                return "Hesabiniz aktif edilmistir.";
            }
            default -> {
                throw new AuthManagerException(ErrorType.BAD_REQUEST);
            }
        }
    }

    public String deleteUserById(Long id) {
        Optional<Auth> auth = findById(id);
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (auth.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        auth.get().setStatus(EStatus.DELETED);
        update(auth.get());
        return auth.get().getId().toString() + " id' li kullanici silinmistir.";
    }

    public Boolean updateAuth(UpdateRequestDto dto, String token) {
        Optional<Long> id = jwtTokenManager.getIdFromToken(token.substring(7));
        if (id.isEmpty()) {
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<Auth> auth = repository.findById(id.get());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }
        try {
            auth.get().setUsername(dto.getUsername());
            auth.get().setEmail(dto.getEmail());
            update(auth.get());
            return true;
        } catch (Exception e) {
            throw new AuthManagerException(ErrorType.AUTH_NOT_UPDATED);
        }
    }


}

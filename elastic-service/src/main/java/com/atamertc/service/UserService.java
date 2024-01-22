package com.atamertc.service;

import com.atamertc.dto.request.PagingRequestDto;
import com.atamertc.exception.ElasticManagerException;
import com.atamertc.exception.ErrorType;
import com.atamertc.manager.UserManager;
import com.atamertc.mapper.UserMapper;
import com.atamertc.rabbitmq.model.ElasticUserSaveModel;
import com.atamertc.repository.UserRepository;
import com.atamertc.repository.entity.User;
import com.atamertc.repository.enums.EStatus;
import com.atamertc.utility.ServiceManager;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService extends ServiceManager<User, String> {
    private final UserRepository repository;
    private final UserManager userManager;

    public UserService(UserRepository repository, UserManager userManager) {
        super(repository);
        this.repository = repository;
        this.userManager = userManager;
    }

    public void findAllUserForElasticAndSave() {
        List<User> users = new ArrayList<>();
        userManager.findAllUser2().getBody().forEach(x -> {
            users.add(UserMapper.INSTANCE.toUser(x));
        });
        for (User user : users) {
            if (!repository.existsByAuthid(user.getAuthid())) {
                save(user);
            }
        }
    }

    public void rabbitSaveElasticUser(ElasticUserSaveModel model) {
        if (repository.existsByAuthid(model.getAuthid())) {
            throw new ElasticManagerException(ErrorType.USER_ALREADY_EXIST);
        }
        User user = UserMapper.INSTANCE.toUser(model);
        try {
            save(user);
        } catch (Exception e) {
            throw new ElasticManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    public Page<User> findAllByPageable(PagingRequestDto dto) {
        //Siralama ve sayfalama icin bize gerekli nesneler
        Pageable pageable = null;
        Sort sort = null;

        if (dto.getSortParameter() != null) {
            String direction = dto.getDirection() == null ? "ASC" : dto.getDirection();
            sort = Sort.by(Sort.Direction.fromString(direction), dto.getSortParameter());
        }

        Integer pageSize = dto.getPageSize() == null ? 10 :
                dto.getPageSize() < 1 ? 10 : dto.getPageSize();

        //hem siralama hem sayfalama secildiyse
        if (sort != null && dto.getCurrentPage() != null) {
            pageable = PageRequest.of(dto.getCurrentPage(), pageSize, sort);
        }
        //siralama yok ama sayfalama secildiyse
        else if (sort == null && dto.getCurrentPage() != null) {
            pageable = PageRequest.of(dto.getCurrentPage(), pageSize);
        }
        //siralama ve sayfalama yok
        else {
            pageable = PageRequest.of(0, pageSize);
        }
        return repository.findAll(pageable);
    }


    public Slice<User> findAllSlice(int pageSize, int pageNumber, String direction, String sortParameter) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortParameter);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return repository.findAll(pageable);
    }

    public List<User> findAllContainingEmail(String value) {
        return repository.findAllByEmailContainingIgnoreCase(value);
    }

    public List<User> findAllByStatus(String status) {
        return repository.findAllByStatus(EStatus.valueOf(status.toUpperCase(Locale.ENGLISH)));
    }

    public List<User> findAllByStatusOrAddress(String status, String address) {
        return repository.findAllByStatusOrAddress(EStatus.valueOf(status.toUpperCase(Locale.ENGLISH)), address);
    }

    public User findByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ElasticManagerException(ErrorType.USER_NOT_FOUND);
        }
        return user.get();
    }
}

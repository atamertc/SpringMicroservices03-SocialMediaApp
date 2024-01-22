package com.atamertc.repository;

import com.atamertc.repository.entity.User;
import com.atamertc.repository.enums.EStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

    Boolean existsByAuthid(Long authid);

    List<User> findAllByEmailContainingIgnoreCase(String value);

    List<User> findAllByStatus(EStatus status);
    List<User> findAllByStatusOrAddress(EStatus status, String address);

    Optional<User> findByUsername(String username);
}

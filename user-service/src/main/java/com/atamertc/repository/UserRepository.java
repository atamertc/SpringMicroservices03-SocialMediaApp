package com.atamertc.repository;

import com.atamertc.repository.entity.User;
import com.atamertc.repository.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Boolean existsByAuthid(Long authid);

    Optional<User> findByUsername(String username);

    Optional<User> findByAuthid(Long authid);

    Optional<List<User>> findByStatus(EStatus status);

    @Query("{'username' : ?0}")
    Optional<User> getUser(String username);

    @Query(" {authid: {$gt: ?0}}")
    List<User> findUserGtId(Long authid);

    @Query("{ $or:[{authId: {$gt: ?0}},{status: ?1}]} ")
    List<User> findUserGtIdAndStatus(Long authId, EStatus status);
}

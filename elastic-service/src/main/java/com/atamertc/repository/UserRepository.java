package com.atamertc.repository;

import com.atamertc.repository.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

    Boolean existsByAuthid(Long authid);
}

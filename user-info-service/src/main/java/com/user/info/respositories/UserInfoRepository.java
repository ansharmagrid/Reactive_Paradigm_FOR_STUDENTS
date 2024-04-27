package com.user.info.respositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.user.info.entities.UserInfoEntity;

public interface UserInfoRepository extends ReactiveMongoRepository<UserInfoEntity, String>{

}

package com.user.info.respositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.user.info.entities.UserInfoEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserInfoRepository extends ReactiveMongoRepository<UserInfoEntity, String>{

	Flux<UserInfoEntity> findByName(String name);
	Flux<UserInfoEntity> findByPhone(String phone);
	Mono<UserInfoEntity> findById(String id);
}

package com.logging.service;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LogRequestRepository extends ReactiveMongoRepository<LogRequest, String>{

}

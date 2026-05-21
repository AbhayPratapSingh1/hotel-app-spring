package com.hotel.app.repository;

import com.hotel.app.views.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}

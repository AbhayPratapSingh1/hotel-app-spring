package com.hotel.app.services;

import com.hotel.app.repository.UserRepository;
import com.hotel.app.views.RegisterRequest;
import com.hotel.app.views.User;
import com.hotel.app.views.UserDetails;
import com.hotel.app.views.loginRequest;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final IdGenerator idGenerator;

    public AuthServiceImpl(UserRepository userRepository, IdGenerator idGenerator) {
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public UserDetails validate(loginRequest loginRequest) {
        return new UserDetails("2",loginRequest.username());
    }

    @Override
    public UserDetails addUser(RegisterRequest registerRequest) {
        User userToRegister = new User(idGenerator.generate(), registerRequest.username(), registerRequest.password());
        User user = userRepository.save(userToRegister);
        return UserDetails.of(user);
    }
}

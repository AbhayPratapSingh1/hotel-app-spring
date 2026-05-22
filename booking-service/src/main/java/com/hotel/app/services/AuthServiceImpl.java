package com.hotel.app.services;

import com.hotel.app.execptions.InvalidCredential;
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
    public UserDetails validate(loginRequest loginRequest) throws InvalidCredential {
        User user = userRepository.findUserByUsername(loginRequest.username());

        boolean valid = user.validate(loginRequest.password());
        if(!valid) throw new InvalidCredential("Password and username did not match");
        return user.details();
    }

    @Override
    public UserDetails addUser(RegisterRequest registerRequest) {
        String password = registerRequest.password();
        User userToRegister = new User(idGenerator.generate(), registerRequest.username(), password);
        User user = userRepository.save(userToRegister);
        return user.details();
    }
}

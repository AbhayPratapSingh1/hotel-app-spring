package com.hotel.app.services;

import com.hotel.app.views.RegisterRequest;
import com.hotel.app.views.UserDetails;
import com.hotel.app.views.loginRequest;

public interface AuthService {
    UserDetails validate(loginRequest loginRequest);

    UserDetails addUser(RegisterRequest registerRequest);
}

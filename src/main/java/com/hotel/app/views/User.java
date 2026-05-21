package com.hotel.app.views;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User{
    @Id
    private final String id;
    private final String username;
    private final String password;

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDetails details() {
       return new UserDetails(id, username);
    }
}

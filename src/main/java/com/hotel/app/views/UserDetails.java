package com.hotel.app.views;

public record UserDetails(String userId, String username) {
    public static UserDetails of(User user) {
        return user.details();
    }
}

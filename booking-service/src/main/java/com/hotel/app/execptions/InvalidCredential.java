package com.hotel.app.execptions;

public class InvalidCredential extends Throwable {
    public InvalidCredential(String message) {
        super(message);
    }
}

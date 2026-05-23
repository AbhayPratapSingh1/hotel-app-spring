package com.hotel.app.views;

import com.hotel.app.services.Status;

public record BookingView(String id , String userId, int roomCount, String name, Status status) {
}

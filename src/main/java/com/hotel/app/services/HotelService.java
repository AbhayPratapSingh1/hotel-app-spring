package com.hotel.app.services;

import com.hotel.app.views.Booking;

import java.util.List;

public interface HotelService {
    List<Booking> listBookings(String userId);
}
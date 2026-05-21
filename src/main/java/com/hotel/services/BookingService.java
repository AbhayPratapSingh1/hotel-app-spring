package com.hotel.services;

import com.hotel.app.views.Booking;
import com.hotel.app.views.BookingsView;

import java.util.List;

public interface BookingService {
    BookingsView listBookings(String userId);
}
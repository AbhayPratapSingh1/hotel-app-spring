package com.hotel.app.services;

import com.hotel.app.model.Hotel;
import com.hotel.app.views.Booking;

import java.util.List;

public interface HotelService {
    List<Hotel> listHotels();

    List<Hotel> listHotelsWithCityName(String city);
    List<Booking> listBookings(String userId);

    Booking bookHotel(String hotelId, int rooms);

    byte[] getReceiptData(String bookingId, String userId);
}
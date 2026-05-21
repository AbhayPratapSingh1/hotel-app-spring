package com.hotel.app.services;

import com.hotel.app.model.Hotel;
import com.hotel.app.views.BookingView;

import java.util.List;

public interface HotelService {
    List<Hotel> listHotels();

    List<Hotel> listHotelsWithCityName(String city);

    List<BookingView> listBookings(String userId);


    BookingView bookHotel(String userId, String hotelId, int rooms);

    byte[] getReceiptData(String bookingId, String userId);

}
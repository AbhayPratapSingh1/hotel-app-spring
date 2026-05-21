package com.hotel.app.controller;

import com.hotel.app.model.Hotel;
import com.hotel.app.services.HotelServiceImpl;
import com.hotel.app.views.Booking;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelServiceImplTest {
    @Test
    @Description("Should return the list of hotels")
    void listHotelsWithCityName() {
        HotelServiceImpl hotelService = new HotelServiceImpl();
        List<Hotel> hotels = hotelService.listHotels();
        List<Hotel> expectedHotels = List.of(
                new Hotel("1", "Taj","New York", 10),
                new Hotel("2", "Shrinivasna", "India", 20)
        );

        assertEquals(expectedHotels, hotels);
    }

    @Test
    @Description("Should return the list of bookings")
    void listBookings() {
        HotelServiceImpl hotelService = new HotelServiceImpl();
        String userId = "1";
        List<Booking> bookings = hotelService.listBookings(userId);
        List<Booking> expectedHotels = List.of(new Booking("1", "Taj", 10));

        assertEquals(expectedHotels,bookings);
    }


    @Test
    @Description("Should return the list of Hotels with given city")
    void listSearchedHotels() {
        HotelServiceImpl hotelService = new HotelServiceImpl();
        List<Hotel> hotels = hotelService.listHotelsWithCityName("New York");
        List<Hotel> expectedHotels = List.of(
                new Hotel("1", "Taj","New York", 10)
        );

        assertEquals(expectedHotels,hotels);
    }

    @Test
    @Description("Should return the Hotel with status when hotel is booked ")
    void bookHotel() {
        HotelServiceImpl hotelService = new HotelServiceImpl();
        Booking booking = hotelService.bookHotel("1", 1);
        Booking expected = new Booking("1", "Taj", 1);

        assertEquals(expected, booking);
    }

}


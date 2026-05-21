package com.hotel.app.controller;

import com.hotel.app.model.Hotel;
import com.hotel.app.services.HotelServiceImpl;
import com.hotel.app.services.IdGenerator;
import com.hotel.app.views.Booking;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class HotelServiceImplTest {

    @MockitoBean
    private IdGenerator idGenerator;

    @Autowired
    private HotelServiceImpl hotelService;



    @Test
    @Description("Should return the list of hotels")
    void listHotelsWithCityName() {
        when(idGenerator.generate()).thenReturn("1", "2", "3");

        List<Hotel> hotels = hotelService.listHotels();
        List<Hotel> expectedHotels = List.of(
                new Hotel(1, "Taj","New York", 10),
                new Hotel(2, "Shrinivasna", "India", 20)
        );

        assertEquals(expectedHotels, hotels);
    }

    @Test
    @Description("Should return the list of bookings")
    void listBookings() {
        when(idGenerator.generate()).thenReturn("1", "2", "3");

        HotelServiceImpl hotelService = new HotelServiceImpl(idGenerator);
        String userId = "1";
        List<Booking> bookings = hotelService.listBookings(userId);
        List<Booking> expectedHotels = List.of(new Booking("1",1, "Taj", 10));

        assertEquals(expectedHotels,bookings);
    }


    @Test
    @Description("Should return the list of Hotels with given city")
    void listSearchedHotels() {
        when(idGenerator.generate()).thenReturn("1", "2", "3");

        HotelServiceImpl hotelService = new HotelServiceImpl(idGenerator);
        List<Hotel> hotels = hotelService.listHotelsWithCityName("New York");
        List<Hotel> expectedHotels = List.of(
                new Hotel(1, "Taj","New York", 10)
        );

        assertEquals(expectedHotels,hotels);
    }

    @Test
    @Description("Should return the Hotel with status when hotel is booked ")
    void bookHotel() {
        when(idGenerator.generate()).thenReturn("1", "2", "3");

        HotelServiceImpl hotelService = new HotelServiceImpl(idGenerator);

        Booking booking = hotelService.bookHotel("1",1, 1);
        Booking expected = new Booking("1",1, "Taj", 1);

        assertEquals(expected, booking);
    }

}


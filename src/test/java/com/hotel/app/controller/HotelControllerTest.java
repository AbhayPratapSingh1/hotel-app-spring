package com.hotel.app.controller;

import com.hotel.app.model.Hotel;import com.hotel.app.views.Booking;
import com.hotel.app.services.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureRestTestClient
class HotelControllerTest {

    @Autowired
    private RestTestClient client;

    @MockitoBean
    private HotelService bookingService;

    @Test
    void listBookingsShouldReturnListOfBookings() {
        Booking booking = new Booking("1","hotel", 1);

        when(bookingService.listBookings("1")).thenReturn(List.of(booking));

        client.get()
                .uri("/api/bookings")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .returnResult();
    }

    @Test
    void listHotelsShouldReturnListOfTheHotelsOfTheHotel() {
        Hotel hotel = new Hotel("1","Taj", "New York", 10);
        when(bookingService.listHotels(anyString())).thenReturn(List.of(hotel));

        client.get()
                .uri("/api/search/hotels?city=New%20York")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .returnResult();
    }
}
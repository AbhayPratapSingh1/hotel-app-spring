package com.hotel.app;

import com.hotel.app.views.Booking;
import com.hotel.app.views.BookingsView;
import com.hotel.services.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureRestTestClient
class HotelControllerTest {

    @Autowired
    private RestTestClient client;

    @MockitoBean
    private BookingService bookingService;

    @Test
    void listBookingsShouldReturnListOfBookings() {
        Booking booking = new Booking("hotel", 1);
        BookingsView bookingsView = new BookingsView();
        bookingsView.add(booking);
        when(bookingService.listBookings("1")).thenReturn(bookingsView);

        List<Booking> responseBody = client.get()
                .uri("/api/bookings")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .returnResult()
                .getResponseBody();

    }
}
package com.hotel.app.controller;

import com.hotel.app.views.Booking;
import com.hotel.app.services.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelController {

    private final HotelService bookingService;

    public HotelController(HotelService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> listBookings() {
        String userId = "1";
        List<Booking> bookings = bookingService.listBookings(userId);
        return ResponseEntity.ok(bookings);
    }
}

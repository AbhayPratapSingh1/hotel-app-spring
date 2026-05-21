package com.hotel.app;

import com.hotel.app.views.Booking;
import com.hotel.app.views.BookingsView;
import com.hotel.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelController {

    private final BookingService bookingService;

    public HotelController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> listBookings() {
        String userId = "1";
        BookingsView bookings = bookingService.listBookings(userId);
        return ResponseEntity.ok(bookings);
    }
}

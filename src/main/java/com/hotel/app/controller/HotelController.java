package com.hotel.app.controller;

import com.hotel.app.model.Hotel;
import com.hotel.app.views.Booking;
import com.hotel.app.services.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> listBookings() {
        String userId = "1";
        List<Booking> bookings = hotelService.listBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/search/hotels")
    public ResponseEntity<?> ListHotelsOfCity(@RequestParam String city) {
        List<Hotel> hotels = hotelService.listHotelsWithCityName(city);
        return ResponseEntity.ok(hotels);
    }
}

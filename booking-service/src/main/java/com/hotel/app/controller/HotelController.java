package com.hotel.app.controller;

import com.hotel.app.model.Hotel;
import com.hotel.app.views.Booking;
import com.hotel.app.services.HotelService;
import com.hotel.app.views.BookingRequest;
import com.hotel.app.views.BookingView;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/")
    public ResponseEntity<?> listBookings() {

        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> listBookings(Authentication auth) {
        String userId = auth.getName();
        List<BookingView> bookings = hotelService.listBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> bookHotel(@RequestBody BookingRequest bookingRequest, Authentication auth) {
        int hotelId = bookingRequest.hotel_id();
        String userId = auth.getName();
        BookingView bookingView = hotelService.bookHotel(userId, String.valueOf(hotelId), bookingRequest.rooms());
        return ResponseEntity.ok(bookingView);
    }

    @GetMapping("/bookings/{bookingId}/{fileName}")
    public ResponseEntity<?> downLoadReceipt(@PathVariable String bookingId, @PathVariable String fileName, Authentication auth) {
        String userId = auth.getName();
        byte[] receiptData = hotelService.getReceiptData(bookingId, userId);
        ByteArrayResource resource = new ByteArrayResource(receiptData);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\"".formatted(fileName));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(receiptData.length));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");

        return ResponseEntity.ok().headers(headers).body(resource);
    }
}

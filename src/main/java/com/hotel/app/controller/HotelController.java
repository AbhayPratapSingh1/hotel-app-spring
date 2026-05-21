package com.hotel.app.controller;

import com.hotel.app.model.Hotel;
import com.hotel.app.views.Booking;
import com.hotel.app.services.HotelService;
import com.hotel.app.views.BookingRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    public ResponseEntity<?> ListHotelsOfCity(@RequestParam(required = false) String city) {
        if(city == null){
            return ResponseEntity.ok(hotelService.listHotels());
        }

        List<Hotel> hotels = hotelService.listHotelsWithCityName(city);
        return ResponseEntity.ok(hotels);
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> bookHotel(@RequestBody BookingRequest bookingRequest) {
        int hotelId = bookingRequest.hotel_id();
        System.out.println(hotelId);
        String userId = "1";
        Booking booking = hotelService.bookHotel(userId, String.valueOf(hotelId), bookingRequest.rooms());
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/bookings/{bookingId}/{fileName}")
    public ResponseEntity<?> downLoadReceipt(@PathVariable String bookingId, @PathVariable String fileName) {
        String userId = "1";
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

package com.hotel.app.services;

import com.hotel.app.repository.BookingRepo;
import com.hotel.app.repository.HotelRepo;


import com.hotel.app.model.Hotel;
import com.hotel.app.views.Booking;
import com.hotel.app.views.BookingView;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private final IdGenerator idGenerator;
    private final HotelRepo hotelRepo;
    private final BookingRepo bookingRepo;

    public HotelServiceImpl(IdGenerator idGenerator, HotelRepo hotelRepo, BookingRepo bookingRepo) {
        this.idGenerator = idGenerator;
        this.hotelRepo = hotelRepo;
        this.bookingRepo = bookingRepo;

        hotelRepo.save(new Hotel("1", "Taj Hotels", "New York", 10));
        hotelRepo.save(new Hotel("2", "Srinivasan", "Bangalore", 10));
    }

    @Override
    public List<Hotel> listHotels() {
        return this.hotelRepo.findAll().stream().toList();
    }

    @Override
    public List<Hotel> listHotelsWithCityName(String city) {
        return this.hotelRepo.findAllByCity(city);
    }


    @Override
    public List<BookingView> listBookings(String userId) {
        return bookingRepo.findAllByUserId(userId).stream().map(each -> each.view()).toList();
    }

    @Override
    public BookingView bookHotel(String userId, String hotelId, int roomsCount) {

        Hotel hotel = hotelRepo.findById(String.valueOf(hotelId)).orElseThrow(()->new RuntimeException("Invalid id"));

        Booking booking = new Booking(idGenerator.generate(), userId, hotelId, hotel.getName(), roomsCount);

        bookingRepo.save(booking);
        return booking.view();
    }

    @Override
    public byte[] getReceiptData(String bookingId, String userId) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Invalid Booking id"));

        String content = "Receipt for %s".formatted(booking.toString());
        return content.getBytes(StandardCharsets.UTF_8);
    }
}

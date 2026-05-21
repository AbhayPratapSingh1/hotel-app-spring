package com.hotel.app.services;

import com.hotel.app.Repository.BookingRepo;
import com.hotel.app.Repository.HotelRepo;
import com.hotel.app.model.Hotel;
import com.hotel.app.views.Booking;
import org.springframework.stereotype.Service;

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
    public List<Booking> listBookings(String userId) {
        return List.of(new Booking(idGenerator.generate(), "1", "Taj", 10));
    }

    @Override
    public Booking bookHotel(String userId, String hotelId, int roomsCount) {

        Hotel hotel = hotelRepo.findById(String.valueOf(hotelId)).orElseThrow(()->new RuntimeException("Invalud id"));


        Booking booking = new Booking(idGenerator.generate(), hotelId, hotel.getName(), roomsCount);
        bookingRepo.save(booking);
        return booking;
    }
}

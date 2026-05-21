package com.hotel.app.services;

import com.hotel.app.model.Hotel;
import com.hotel.app.views.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Override
    public List<Hotel> listHotels() {
        return List.of(
                new Hotel("1", "Taj","New York", 10),
                new Hotel("2", "Shrinivasna", "India", 20)
        );
    }

    @Override
    public List<Hotel> listHotelsWithCityName(String city) {
        List<Hotel> hotels = listHotels();
        return hotels.stream().filter(h -> h.matchCity(city)).toList();
    }


    @Override
    public List<Booking> listBookings(String userId) {
        return List.of(new Booking("1", "Taj", 10));
    }
    @Override
    public Booking bookHotel(String hotelId, int roomsCount) {
        List<Hotel> hotels = listHotels();
        Hotel hotel = hotels.stream().filter(h -> h.matchId(hotelId)).toList().get(0);
        return new Booking(hotelId, hotel.getName(), roomsCount);
    }
}

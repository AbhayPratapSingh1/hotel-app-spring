package com.hotel.app.repository;

import com.hotel.app.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepo extends MongoRepository< Hotel, String> {
    List<Hotel> findAllByCity(String city);
}

package com.hotel.app.Repository;

import com.hotel.app.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepo extends MongoRepository< Hotel, String> {


    List<Hotel> findAllByCity(String city);

}

package com.hotel.app.repository;

import com.hotel.app.services.Status;
import com.hotel.app.views.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends MongoRepository<Booking, String> {
    List<Booking> findAllByUserId(String userId);

    @Query("{'_id' :  ?0}")
    @Update("{'$set' : {'status' : ?1 }}")
    void updateStatusById(String bookingId, Status status);
}
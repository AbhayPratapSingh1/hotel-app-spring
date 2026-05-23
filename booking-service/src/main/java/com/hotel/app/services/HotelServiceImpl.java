package com.hotel.app.services;

import com.hotel.app.repository.BookingRepo;
import com.hotel.app.views.Booking;
import com.hotel.app.views.BookingView;
import com.hotel.app.views.HotelDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.RedisClient;
import tools.jackson.databind.ObjectMapper;

import java.lang.constant.ConstantDesc;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {
    private final IdGenerator idGenerator;
    private final BookingRepo bookingRepo;
    private  final Jedis jedis;


    @Value("${security.uri.search-uri}")
    private String searchUri;

    public HotelServiceImpl(IdGenerator idGenerator, BookingRepo bookingRepo) {
        this.idGenerator = idGenerator;
        this.bookingRepo = bookingRepo;
        this.jedis = new Jedis("localhost", 6379);
    }

    @Override
    public List<BookingView> listBookings(String userId) {
        return bookingRepo.findAllByUserId(userId).stream().map(each -> each.view()).toList();
    }

    @Override
    public BookingView bookHotel(String userId, String hotelId, int roomsCount) {
        RestClient restClient = RestClient.create();
        HotelDetails hotelDetails = restClient.get().uri("%s/api/internal/book/%s".formatted(searchUri, hotelId)).retrieve().body(HotelDetails.class);
        String bookingId = idGenerator.generate();
        Booking booking = new Booking(bookingId, userId, hotelId, hotelDetails.name(), roomsCount, Status.PENDING);

        jedis.lpush("BOOKING_QUEUE",booking.JsonString());
        bookingRepo.save(booking);
        return booking.view();
    }

    @Override
    public byte[] getReceiptData(String bookingId, String userId) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Invalid Booking id"));
        if (!booking.getUserId().equals(userId)) {
            throw new RuntimeException("Not your booking");
        }
        String content = "Receipt for %s".formatted(booking.toString());
        return content.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void updateStatus(String bookingId) {
        bookingRepo.updateStatusById(bookingId, Status.COMPLETED);
    }
}

package com.hotel.app.services;

import com.hotel.app.model.Hotel;
import com.hotel.app.repository.BookingRepo;
import com.hotel.app.repository.HotelRepo;
import com.hotel.app.views.Booking;
import com.hotel.app.views.BookingView;
import com.hotel.app.views.HotelDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private final IdGenerator idGenerator;
    private final BookingRepo bookingRepo;

    @Value("${security.uri.search-uri}")
    private String searchUri;

    @PostConstruct
    void Something(){
        System.out.println("\n\n\n\n\n\n\n " + searchUri);
        System.out.println(System.getenv("SEARCH_URI"));
        System.out.println("\n\n\n\n\n\n\n " );
    }

    public HotelServiceImpl(IdGenerator idGenerator, BookingRepo bookingRepo) {
        this.idGenerator = idGenerator;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<BookingView> listBookings(String userId) {
        return bookingRepo.findAllByUserId(userId).stream().map(each -> each.view()).toList();
    }

    @Override
    public BookingView bookHotel(String userId, String hotelId, int roomsCount) {

        RestClient restClient = RestClient.create();
        System.out.println(restClient.get().uri("%s/api/internal/book/%s".formatted(searchUri, hotelId)).retrieve());
        HotelDetails hotelDetails = restClient.get().uri("%s/api/internal/book/%s".formatted(searchUri, hotelId)).retrieve().body(HotelDetails.class);

        Booking booking = new Booking(idGenerator.generate(), userId, hotelId, hotelDetails.name(), roomsCount);

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
}

package com.hotel.app.views;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public final class Booking {
    @Id
    private final String id;
    private final String userId;
    private final String hotelId;
    private final String hotel;
    private final int roomCount;

    public Booking(String id, String userId, String hotelId, String hotel, int roomCount) {
        this.id = id;
        this.userId = userId;
        this.hotelId = hotelId;
        this.hotel = hotel;
        this.roomCount = roomCount;
    }

    public String id() {
        return id;
    }

    public String hotelId() {
        return hotelId;
    }

    public String hotel() {
        return hotel;
    }

    public int roomCount() {
        return roomCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Booking) obj;
        return Objects.equals(this.id, that.id) &&
                this.hotelId == that.hotelId &&
                Objects.equals(this.hotel, that.hotel) &&
                this.roomCount == that.roomCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotelId, hotel, roomCount);
    }

    @Override
    public String toString() {
        return "Booking\n" +
                "id        = " + id +
                "\nhotelId   = " + hotelId +
                "\nhotel     = " + hotel +
                "\nroomCount = " + roomCount;
    }


    public String getUserId() {
        return userId;
    }

    public BookingView view() {
        return new BookingView(this.id, this.userId, this.roomCount, this.hotel);
    }
}

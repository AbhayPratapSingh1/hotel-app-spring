package com.hotel.app.model;

import java.util.Objects;

public class Hotel {
    private final String hotel_id;
    private final String name;
    private final String city;
    private final int rooms;



    public Hotel(String hotel_id, String name, String city, int rooms) {
        this.hotel_id = hotel_id;
        this.name = name;
        this.city = city;
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Hotel hotel = (Hotel) object;
        return rooms == hotel.rooms && Objects.equals(hotel_id, hotel.hotel_id) && Objects.equals(name, hotel.name) && Objects.equals(city, hotel.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotel_id, name, city, rooms);
    }
}

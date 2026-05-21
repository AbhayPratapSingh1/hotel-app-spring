package com.hotel.app.model;

import java.util.Objects;

public class Hotel {
    private final String hotelId;
    private final String name;
    private final String city;
    private final int rooms;



    public Hotel(String hotelId, String name, String city, int rooms) {
        this.hotelId = hotelId;
        this.name = name;
        this.city = city;
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Hotel hotel = (Hotel) object;
        return rooms == hotel.rooms && Objects.equals(hotelId, hotel.hotelId) && Objects.equals(name, hotel.name) && Objects.equals(city, hotel.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, name, city, rooms);
    }


    public boolean matchCity(String cityToSearch) {
        return this.city.equals(cityToSearch);
    }

    public String getName() {
        return this.name;
    }

    public boolean matchId(String hotelId) {
        return  hotelId.equals(this.hotelId);
    }
}

package com.hotel.app.views;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HotelDetails(
        @JsonProperty("_id")
        String id,
        String name,
        String city,
        int rooms
) {
}

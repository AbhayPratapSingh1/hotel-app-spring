package com.hotel.app;

import com.hotel.app.Repository.HotelRepo;
import com.hotel.app.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelApp {
	public static void main(String[] args) {

		SpringApplication.run(HotelApp.class, args);
	}
}
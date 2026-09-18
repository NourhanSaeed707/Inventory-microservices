package com.example.booking_service.service;

import com.example.booking_service.dto.BookingDTO;
import com.example.booking_service.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingDTO create(BookingDTO bookingDTO) {
    }
}

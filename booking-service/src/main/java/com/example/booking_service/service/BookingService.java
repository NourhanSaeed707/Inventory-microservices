package com.example.booking_service.service;

import com.example.booking_service.dto.BookingDTO;
import com.example.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingDTO create(BookingDTO bookingDTO) {
    }
}

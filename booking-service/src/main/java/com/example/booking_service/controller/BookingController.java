package com.example.booking_service.controller;
import com.example.booking_service.dto.BookingDTO;
import com.example.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/")
    public ResponseEntity<BookingDTO> create(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.create(bookingDTO));
    }
}

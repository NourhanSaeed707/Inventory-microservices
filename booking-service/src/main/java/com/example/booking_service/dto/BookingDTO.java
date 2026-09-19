package com.example.booking_service.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {
    private Long userId;
    private Long eventId;
    private Long ticketCount;
}

package com.example.booking_service.dto;
import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {
    private Long id;
    private String name;
    private Long totalCapacity;
    private Long leftCapacity;
    private VenueDTO venu;
    private BigDecimal ticketPrice;
}

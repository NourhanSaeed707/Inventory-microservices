package com.example.booking_service.dto;
import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDTO {
    private Long eventId;
    private String event;
    private Long capacity;
    private VenueDTO venue;
    private BigDecimal ticketPrice;
}

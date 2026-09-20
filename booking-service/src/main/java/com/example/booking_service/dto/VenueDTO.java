package com.example.booking_service.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VenueDTO {
    private Long id;
    private String name;
    private Long totalCapacity;
}

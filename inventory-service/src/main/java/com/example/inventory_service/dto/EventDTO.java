package com.example.inventory_service.dto;
import com.example.inventory_service.entity.Venue;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  EventDTO {
    private Long id;
    private String name;
    private Long totalCapacity;
    private Long leftCapacity;
    private Venue venu;
}

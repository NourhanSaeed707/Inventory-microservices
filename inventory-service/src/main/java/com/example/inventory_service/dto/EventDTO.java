package com.example.inventory_service.dto;
import com.example.inventory_service.entity.Venu;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {
    private Long id;
    private String name;
    private Long totalCapacity;
    private Long leftCapacity;
    private Venu venu;
}

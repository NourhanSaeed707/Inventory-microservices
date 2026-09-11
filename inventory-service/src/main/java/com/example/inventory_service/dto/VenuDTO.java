package com.example.inventory_service.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VenuDTO {
    private Long id;
    private String name;
    private Long totalCapacity;
}

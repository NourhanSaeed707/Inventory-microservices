package com.example.inventory_service.service.mapper;
import com.example.inventory_service.dto.VenuDTO;
import com.example.inventory_service.entity.Venue;
import org.springframework.stereotype.Service;

@Service
public class VenuMapper {

    public Venue toVenuDTO(VenuDTO venuDTO) {
        return Venue.builder()
                .name(venuDTO.getName())
                .totalCapacity(venuDTO.getTotalCapacity())
                .build();
    }

    public VenuDTO toVenuDTO(Venue venu) {
        return VenuDTO.builder()
                .id(venu.getId())
                .name(venu.getName())
                .totalCapacity(venu.getTotalCapacity())
                .build();
    }
}

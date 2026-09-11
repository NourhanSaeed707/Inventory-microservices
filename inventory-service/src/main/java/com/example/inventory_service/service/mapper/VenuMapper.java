package com.example.inventory_service.service.mapper;
import com.example.inventory_service.dto.VenuDTO;
import com.example.inventory_service.entity.Venu;
import org.springframework.stereotype.Service;

@Service
public class VenuMapper {

    public Venu toVenuDTO(VenuDTO venuDTO) {
        return Venu.builder()
                .name(venuDTO.getName())
                .totalCapacity(venuDTO.getTotalCapacity())
                .build();
    }

    public VenuDTO toVenuDTO(Venu venu) {
        return VenuDTO.builder()
                .id(venu.getId())
                .name(venu.getName())
                .totalCapacity(venu.getTotalCapacity())
                .build();
    }
}

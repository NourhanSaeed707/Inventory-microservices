package com.example.inventory_service.service.mapper;
import com.example.inventory_service.dto.EventDTO;
import com.example.inventory_service.entity.Event;
import com.example.inventory_service.response.EventInventoryResponse;
import org.springframework.stereotype.Service;

@Service
public class EventMapper {

    public Event toEvent(EventDTO eventDTO) {
        return Event.builder()
                .name(eventDTO.getName())
                .leftCapacity(eventDTO.getLeftCapacity())
                .totalCapacity(eventDTO.getTotalCapacity())
                .venue(eventDTO.getVenu())
                .build();

    }

    public EventInventoryResponse toEventInventoryResponse(Event event) {
        return EventInventoryResponse.builder()
                .eventId(event.getId())
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
                .build();
    }
}

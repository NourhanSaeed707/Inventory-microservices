package com.example.inventory_service.service;
import com.example.inventory_service.dto.EventDTO;
import com.example.inventory_service.dto.VenuDTO;
import com.example.inventory_service.entity.Event;
import com.example.inventory_service.entity.Venue;
import com.example.inventory_service.repository.EventRepository;
import com.example.inventory_service.repository.VenuRepository;
import com.example.inventory_service.response.EventInventoryResponse;
import com.example.inventory_service.response.VenueInventoryResponse;
import com.example.inventory_service.service.mapper.EventMapper;
import com.example.inventory_service.service.mapper.VenuMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final EventRepository eventRepository;
    private final VenuRepository venuRepository;
    private final VenuMapper venuMapper;
    private final EventMapper eventMapper;
    public List<EventDTO> getAllEvents;

    public List<EventInventoryResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(eventMapper::toEventInventoryResponse).toList();

    }

    public VenueInventoryResponse getVenueInformation(Long venuId) {
        Venue venu = venuRepository.findById(venuId).orElseThrow(() -> new EntityNotFoundException("Venu with id " + venuId + " not found"));
        return venuMapper.toVenueInventoryResponse(venu);
    }

    public EventInventoryResponse getEventInventory(Long eventId) {
        final Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found"));
        System.out.println("event = " + event);
        return eventMapper.toEventInventoryResponse(event);
    }
}

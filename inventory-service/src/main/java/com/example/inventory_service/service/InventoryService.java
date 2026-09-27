package com.example.inventory_service.service;
import com.example.inventory_service.dto.EventDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
        return eventMapper.toEventInventoryResponse(event);
    }

    public void updateEventCapacity(final Long eventId,final Long ticketsBooked) {
        final Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found"));
        event.setLeftCapacity(event.getLeftCapacity() - ticketsBooked);
        eventRepository.saveAndFlush(event);
        log.info("Updated event capacity for event id {}  with tickets booked {}", eventId, ticketsBooked);
    }
}

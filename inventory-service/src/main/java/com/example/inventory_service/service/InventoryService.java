package com.example.inventory_service.service;

import com.example.inventory_service.dto.EventDTO;
import com.example.inventory_service.entity.Event;
import com.example.inventory_service.repository.EventRepository;
import com.example.inventory_service.repository.VenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final EventRepository eventRepository;
    private final VenuRepository venuRepository;

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
    }
}

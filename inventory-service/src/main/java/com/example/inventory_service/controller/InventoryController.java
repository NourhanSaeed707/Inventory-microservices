package com.example.inventory_service.controller;
import com.example.inventory_service.dto.EventDTO;
import com.example.inventory_service.dto.VenuDTO;
import com.example.inventory_service.response.EventInventoryResponse;
import com.example.inventory_service.response.VenueInventoryResponse;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/events")
    public ResponseEntity<List<EventInventoryResponse>> getInventoryEvents() {
        return ResponseEntity.ok(inventoryService.getAllEvents());
    }

    @GetMapping("/venue/{venuId}")
    public ResponseEntity<VenueInventoryResponse> inventoryByVenueId(@PathVariable Long venuId) {
        return ResponseEntity.ok(inventoryService.getVenueInformation(venuId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<EventInventoryResponse> inventoryForEvent(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(inventoryService.getEventInventory(eventId));
    }

    @PutMapping("/inventory/event/{eventId}/capacity/{capacity}")
    public ResponseEntity<Void> updateEventCapacity(@PathVariable Long eventId, @PathVariable("capacity") Long ticketsBooked) {
        inventoryService.updateEventCapacity(eventId, ticketsBooked);
        return ResponseEntity.ok().build();
    }

}

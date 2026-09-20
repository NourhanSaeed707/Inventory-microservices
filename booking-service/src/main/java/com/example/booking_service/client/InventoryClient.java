package com.example.booking_service.client;
import com.example.booking_service.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service", url = "${application.config.inventory-url}")
public interface InventoryClient {
    @GetMapping("/event/${eventId}")
    ResponseEntity<EventDTO> inventoryForEvent(@PathVariable("eventId") Long eventId);
}

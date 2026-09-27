package com.example.order_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "inventory-service", url = "${application.config.inventory-url}")
public interface InventoryServiceClient {
    @PutMapping("/inventory/event/{eventId}/capacity/{capacity}")
    ResponseEntity<Void> updateEventCapacity(@PathVariable Long eventId, @PathVariable("capacity") Long ticketsBooked);
}

package com.example.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(
        name = "inventory-service",
        url = "${application.config.inventory-url}"
)
public interface InventoryServiceClient {

    @PutMapping("/event/{eventId}/capacity/{capacity}")
    void updateEventCapacity(
            @PathVariable("eventId") Long eventId,
            @PathVariable("capacity") Long ticketsBooked
    );
}
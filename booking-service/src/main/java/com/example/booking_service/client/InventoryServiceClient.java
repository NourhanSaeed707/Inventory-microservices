package com.example.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@FeignClient(name = "inventory-service", url = "${application.config.inventory-url}")
public class InventoryServiceClient {
    
}

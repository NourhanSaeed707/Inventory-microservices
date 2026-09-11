package com.example.inventory_service.controller;
import com.example.inventory_service.dto.EventDTO;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("inventory/events")
    public ResponseEntity<List<EventDTO>> getInventoryEvents() {
        return ResponseEntity.ok(inventoryService.getAllEvents);
    }

}

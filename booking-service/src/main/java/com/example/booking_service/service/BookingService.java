package com.example.booking_service.service;
import com.example.booking_service.client.InventoryClient;
import com.example.booking_service.dto.BookingDTO;
import com.example.booking_service.dto.EventDTO;
import com.example.booking_service.dto.InventoryDTO;
import com.example.booking_service.entity.Customer;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.repository.CustomerRepository;
import com.example.booking_service.request.BookingRequest;
import com.example.booking_service.response.BookingResponse;
import com.example.booking_service.response.InventoryResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final InventoryClient inventoryClient;

    public BookingResponse create(BookingRequest request) {
        final Customer customer = customerRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("user not found wit id "  + request.getUserId()));
        final ResponseEntity<InventoryResponse> inventoryResponse = inventoryClient.inventoryForEvent(request.getEventId());
        System.out.println("inventory: " + inventoryResponse);
        return null;
    }
}

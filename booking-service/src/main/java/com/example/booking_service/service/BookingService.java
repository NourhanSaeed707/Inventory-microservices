package com.example.booking_service.service;
import com.example.booking_service.client.InventoryClient;
import com.example.booking_service.dto.BookingDTO;
import com.example.booking_service.dto.EventDTO;
import com.example.booking_service.dto.InventoryDTO;
import com.example.booking_service.entity.Customer;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.repository.CustomerRepository;
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

    public BookingDTO create(BookingDTO bookingDTO) {
        final Customer customer = customerRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("user not found wit id "  + bookingDTO.getUserId()));
        final ResponseEntity<EventDTO> inventoryResponse = inventoryClient.inventoryForEvent(bookingDTO.getEventId());
        System.out.println("inventory: " + inventoryResponse);
        return bookingDTO;
    }
}

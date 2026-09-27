package com.example.booking_service.service;
import com.example.booking_service.client.InventoryClient;
import com.example.booking_service.entity.Customer;
import com.example.booking_service.event.BookingEvent;
import com.example.booking_service.repository.CustomerRepository;
import com.example.booking_service.request.BookingRequest;
import com.example.booking_service.response.BookingResponse;
import com.example.booking_service.response.InventoryResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final CustomerRepository customerRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public BookingResponse create(BookingRequest request) {
        final Customer customer = customerRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("user not found wit id "  + request.getUserId()));
        final ResponseEntity<InventoryResponse> inventoryResponse = inventoryClient.inventoryForEvent(request.getEventId());
        final InventoryResponse inventory = inventoryResponse.getBody();
        log.info("inventory: " + inventory);
        if(inventory!= null && inventory.getCapacity() < request.getTicketCount()) {
            throw  new RuntimeException("Capacity less than ticket count");
        }
        final BookingEvent bookingEvent = createBookingEvent(request, customer, inventory);
        kafkaTemplate.send("booking_event", bookingEvent);
        log.info("Booking sent to Kafka: ", bookingEvent);
        return BookingResponse.builder().build();
    }

    private  BookingEvent createBookingEvent(final BookingRequest request,final Customer customer,final InventoryResponse inventoryResponse) {
       return BookingEvent.builder()
               .userId(customer.getId())
               .eventId(request.getEventId())
               .ticketCount(request.getTicketCount())
               .totalPrice(inventoryResponse.getTicketPrice().multiply(BigDecimal.valueOf(request.getTicketCount())))
               .build();
    }
}

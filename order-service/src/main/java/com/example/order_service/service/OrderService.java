package com.example.order_service.service;
import com.example.booking_service.event.BookingEvent;
import com.example.order_service.client.InventoryServiceClient;
import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private OrderRepository orderRepository;
    private InventoryServiceClient inventoryServiceClient;

    @KafkaListener(topics = "booking", groupId = "order-service")
    public void orderEvent(BookingEvent bookingEvent) {
        log.info("Received order event: {}", bookingEvent);
        // Create Order object for DB
        Order order = createOrder(bookingEvent);
        orderRepository.saveAndFlush(order);
        // Update Inventory
        inventoryServiceClient.updateEventCapacity(order.getEventId(), order.getTicketCount());
        log.info("Inventory updated for event{}:, less tickets: {}", order.getEventId(), order.getTicketCount());

    }

    private Order createOrder(BookingEvent bookingEvent) {
        return Order.builder()
                .customerId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }

}

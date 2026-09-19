package com.example.booking_service.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "customer")
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String address;
}

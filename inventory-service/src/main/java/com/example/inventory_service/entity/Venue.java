package com.example.inventory_service.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "events")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    @Column(name = "total_capacity")
    private Long totalCapacity;

    @OneToMany(mappedBy = "venue")
    List<Event> events;
}

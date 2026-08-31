package com.busbooking.trip.entity;

import com.busbooking.route.entity.City;
import com.busbooking.route.entity.Route;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "pick_up_trip")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PickupTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private String name;

    private String address;

    private String status;

    @Column(name = "map_url")
    private String mapUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;
}
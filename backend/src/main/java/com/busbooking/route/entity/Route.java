package com.busbooking.route.entity;

import com.busbooking.trip.entity.PickupTrip;
import com.busbooking.trip.entity.Trip;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estimate_distance")
    private BigDecimal estimateDistance;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_city_id", nullable = false)
    private City originCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destinationCity;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<PickupTrip> pickupTrips;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Trip> trips;
}
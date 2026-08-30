package com.busbooking.route.entity;

import com.busbooking.trip.entity.PickupTrip;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(nullable = false)
    private String name;

    private String status;

    @OneToMany(mappedBy = "originCity", cascade = CascadeType.ALL)
    private List<Route> routesAsOrigin;

    @OneToMany(mappedBy = "destinationCity", cascade = CascadeType.ALL)
    private List<Route> routesAsDestination;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<PickupTrip> pickupTrips;
}
package com.busticket.route;

import com.busticket.city.City;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "estimate_distance")
    private BigDecimal estimateDistance;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_city_id", nullable = false)
    private City originCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destinationCity;

    // pickupTrips va trips duoc quan ly boi package pickuptrip / trip tuong ung,
    // khong khai bao @OneToMany nguoc lai o day de tranh phu thuoc chieu nguoc
    // (route khong can biet ve trip/pickupTrip, chi trip/pickupTrip can biet ve route)
}

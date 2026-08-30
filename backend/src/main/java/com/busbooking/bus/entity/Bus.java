package com.busbooking.bus.entity;

import com.busbooking.operator.entity.Operator;
import com.busbooking.trip.entity.Trip;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "bus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bus_type")
    private String busType;

    @Column(name = "seat_count")
    private Integer seatCount;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;

    private String status;

    private String brand;

    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<BusSeat> busSeats;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<Trip> trips;
}
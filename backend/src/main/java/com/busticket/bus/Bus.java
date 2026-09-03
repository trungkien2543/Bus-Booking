package com.busticket.bus;

import com.busticket.operator.Operator;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "bus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

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

    // busSeat va trip duoc quan ly boi package tuong ung, khong khai bao @OneToMany nguoc lai o day
}
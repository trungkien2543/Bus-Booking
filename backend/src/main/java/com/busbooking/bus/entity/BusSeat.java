package com.busbooking.bus.entity;

import com.busbooking.booking.entity.BookingSeat;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bus_seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "seat_type")
    private String seatType;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "seat_side")
    private String seatSide;

    private String position;

    @Column(name = "row_number")
    private Integer rowNumber;

    @Column(name = "column_number")
    private Integer columnNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @OneToMany(mappedBy = "busSeat", cascade = CascadeType.ALL)
    private List<BookingSeat> bookingSeats;
}
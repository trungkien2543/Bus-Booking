package com.busticket.booking;

import com.busticket.busSeat.BusSeat;
import com.busticket.trip.Trip;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * UNIQUE(trip_id, bus_seat_id) - dam bao 1 ghe khong the bi dat 2 lan
 * cho cung 1 trip, du co 2 request insert cung luc (day la lop bao ve
 * cuoi cung o tang DB, phong khi Redis lock bi mat/loi).
 * LUU Y: khi 1 booking bi CANCELLED, phai XOA han dong booking_seat
 * tuong ung (khong chi doi status), neu khong ghe se bi khoa vinh vien.
 */
@Entity
@Table(
        name = "booking_seat",
        uniqueConstraints = @UniqueConstraint(columnNames = {"trip_id", "bus_seat_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_seat_id", nullable = false)
    private BusSeat busSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}

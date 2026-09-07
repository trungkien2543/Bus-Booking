package com.busticket.tripstop;

import com.busticket.location.Location;
import com.busticket.trip.Trip;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Du lieu THAT, thuoc ve 1 Trip cu the - duoc he thong tu sinh tu
 * RouteStopTemplate khi tao trip moi (stopTime = trip.departureTime +
 * offsetMinutes cua template). Admin co the sua tay rieng le neu can.
 * Booking se tham chieu truc tiep vao day (khong tham chieu vao Location).
 */
@Entity
@Table(name = "trip_stop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripStop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "stop_type", nullable = false)
    private String stopType; // PICKUP hoac DROPOFF

    @Column(name = "stop_time", nullable = false)
    private LocalDateTime stopTime;

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder;

    private String status;

    // booking (pickUpStop/dropOffStop) duoc quan ly boi package booking,
    // khong khai bao @OneToMany nguoc lai o day
}

package com.busticket.booking;

import com.busticket.pickupTrip.PickupTrip;
import com.busticket.trip.Trip;
import com.busticket.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private String status;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pick_up_point_id")
    private PickupTrip pickUpPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drop_off_point_id")
    private PickupTrip dropOffPoint;

    // bookingSeat va payment duoc quan ly boi package tuong ung,
    // khong khai bao @OneToMany / @OneToOne nguoc lai o day
}

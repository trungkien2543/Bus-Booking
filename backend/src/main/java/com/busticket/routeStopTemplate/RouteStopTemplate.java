package com.busticket.routestoptemplate;

import com.busticket.location.Location;
import com.busticket.route.Route;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Mau lich trinh diem dung cho 1 Route - de admin khong phai nhap tay
 * tung diem cho tung trip. offsetMinutes la do lech thoi gian so voi
 * departure_time cua trip (KHONG luu gio cung).
 * He thong dung mau nay de tu sinh TripStop khi tao trip moi.
 */
@Entity
@Table(name = "route_stop_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStopTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "stop_type", nullable = false)
    private String stopType; // PICKUP hoac DROPOFF

    @Column(name = "offset_minutes", nullable = false)
    private Integer offsetMinutes;

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder;
}

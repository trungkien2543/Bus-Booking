package com.busticket.location;

import com.busticket.city.City;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Du lieu goc, doc lap - 1 diem don/tra vat ly (VD "Ben xe Mien Dong").
 * Khong gan voi route hay trip nao ca, dung lai duoc cho nhieu
 * RouteStopTemplate / TripStop khac nhau.
 */
@Entity
@Table(name = "location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(name = "map_url")
    private String mapUrl;

    private String status;

    // routeStopTemplate va tripStop duoc quan ly boi package tuong ung,
    // khong khai bao @OneToMany nguoc lai o day
}

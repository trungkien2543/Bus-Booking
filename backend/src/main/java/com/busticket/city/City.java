package com.busticket.city;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(nullable = false)
    private String name;

    private String status;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    // route va location duoc quan ly boi package tuong ung,
    // khong khai bao @OneToMany nguoc lai o day
}

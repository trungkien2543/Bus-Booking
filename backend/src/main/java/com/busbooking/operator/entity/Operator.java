package com.busbooking.operator.entity;

import com.busbooking.bus.entity.Bus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "operator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String status;

    private String phone;

    @Column(length = 1000)
    private String description;

    private String address;

    private String email;

    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL)
    private List<Bus> buses;
}
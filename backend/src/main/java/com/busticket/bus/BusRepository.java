package com.busticket.bus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BusRepository extends JpaRepository<Bus, UUID> {

    List<Bus> findByOperatorId(UUID operatorId);
}

package com.busticket.trip;

import java.util.UUID;

/**
 * Ket qua query 3 - tong hop diem don/tra theo tung trip.
 */
public interface StopSummaryProjection {
    UUID getTripId();
    String getPickupPointsRaw();
    String getDropoffPointsRaw();
}

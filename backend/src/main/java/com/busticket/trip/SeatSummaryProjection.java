package com.busticket.trip;

import java.util.UUID;

/**
 * Ket qua query 2 - tong hop loai ghe / vi tri ghe theo tung bus.
 * Cac truong "...Raw" o dang chuoi phan cach boi dau phay (VD "VIP,NORMAL"),
 * TripService se tach thanh List<String>.
 */
public interface SeatSummaryProjection {
    UUID getBusId();
    String getSeatTypesRaw();
    String getSeatSidesRaw();
    String getSeatPositionsRaw();
}

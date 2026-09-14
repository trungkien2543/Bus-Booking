package com.busticket.booking.repository;

import com.busticket.booking.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, UUID> {

    List<BookingSeat> findByBookingId(UUID bookingId);

    /**
     * Tra ve cac seatId trong danh sach truyen vao ma DA BI CHIEM cho trip nay
     * (dang CONFIRMED, hoac PENDING nhung chua het han giu cho).
     * Dung lam DB fallback khi Redis die, va dung de kiem tra truoc khi insert
     * booking_seat that su (tranh dua vao UNIQUE constraint bao loi ra ngoai).
     */
    @Query("""
            SELECT bs.busSeat.id FROM BookingSeat bs
            WHERE bs.trip.id = :tripId
              AND bs.busSeat.id IN :busSeatIds
              AND (bs.booking.status = 'CONFIRMED'
                   OR (bs.booking.status = 'PENDING' AND bs.booking.expiresAt > CURRENT_TIMESTAMP))
            """)
    List<UUID> findTakenSeatIds(@Param("tripId") UUID tripId, @Param("busSeatIds") List<UUID> busSeatIds);

    @Modifying
    void deleteByBookingId(UUID bookingId);
}

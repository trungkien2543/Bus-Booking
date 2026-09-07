package com.busticket.tripstop;

import com.busticket.tripstop.dto.TripStopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripStopService {

    private final TripStopRepository tripStopRepository;
    private final TripStopMapper tripStopMapper;

    public List<TripStopResponse> getStopsByTrip(UUID tripId) {
        return tripStopMapper.toResponseList(tripStopRepository.findByTripIdOrderBySequenceOrderAsc(tripId));
    }

    /**
     * Dung cho man hinh dat ve: chi lay diem PICKUP hoac chi lay diem DROPOFF
     * cua 1 trip cu the, sap xep theo gio de nguoi dung chon diem gan minh nhat.
     */
    public List<TripStopResponse> getStopsByTripAndType(UUID tripId, String stopType) {
        return tripStopMapper.toResponseList(
                tripStopRepository.findByTripIdAndStopTypeOrderByStopTimeAsc(tripId, stopType)
        );
    }
}

package com.busticket.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Ket qua tim kiem chuyen di. outboundTrips luon co gia tri (theo
 * departureTime, bat buoc). returnTrips CHI co gia tri khi nguoi dung
 * co truyen returnDate (tim khu hoi) - neu tim 1 chieu, returnTrips = null.
 */
@Getter
@AllArgsConstructor
public class TripSearchResultResponse {
    private List<TripSearchResponse> outboundTrips;
    private List<TripSearchResponse> returnTrips;
}

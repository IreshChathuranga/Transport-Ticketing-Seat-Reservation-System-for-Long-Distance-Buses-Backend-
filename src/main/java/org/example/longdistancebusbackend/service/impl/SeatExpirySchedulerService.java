package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.entity.TripSeat;
import org.example.longdistancebusbackend.repository.TripSeatRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//@Service
//@RequiredArgsConstructor
public class SeatExpirySchedulerService {
//    private final TripSeatRepository tripSeatRepository;
//
//    @Scheduled(fixedRate = 60000) // run every 1 minute
//    public void releaseExpiredSeats() {
//        List<TripSeat> expiredSeats = tripSeatRepository.findByStatusAndHoldExpiresAtBefore(
//                "HELD", LocalDateTime.now()
//        );
//
//        for (TripSeat seat : expiredSeats) {
//            seat.setStatus("AVAILABLE");
//            seat.setHoldExpiresAt(null);
//        }
//
//        if (!expiredSeats.isEmpty()) {
//            tripSeatRepository.saveAll(expiredSeats);
//        }
//    }
}

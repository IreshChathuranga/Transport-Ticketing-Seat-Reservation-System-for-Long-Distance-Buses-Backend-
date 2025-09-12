package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.TripSeat;
import org.example.longdistancebusbackend.repository.BookingRepository;
import org.example.longdistancebusbackend.repository.TripSeatRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//@Service
//@RequiredArgsConstructor
public class BookingExpirySchedulerService {
//    private final BookingRepository bookingRepository;
//    private final TripSeatRepository tripSeatRepository;
//
//    @Scheduled(fixedRate = 60000) // every 1 min
//    public void releaseExpiredBookings() {
//        LocalDateTime now = LocalDateTime.now();
//
//        // find expired bookings
//        List<Booking> expiredBookings = bookingRepository.findByStatusAndExpiresAtBefore("HELD", now);
//
//        for (Booking booking : expiredBookings) {
//            // Update booking status
//            booking.setStatus("EXPIRED");
//
//            // Release seats
//            List<String> seatNumbers = List.of(booking.getSeatNumber().split(","));
//            List<TripSeat> seats = tripSeatRepository.findByTrip_TripIdAndSeat_SeatNumberIn(
//                    booking.getTrip().getTripId(), seatNumbers
//            );
//            for (TripSeat seat : seats) {
//                seat.setStatus("AVAILABLE");
//                seat.setHoldExpiresAt(null);
//            }
//            tripSeatRepository.saveAll(seats);
//        }
//
//        if (!expiredBookings.isEmpty()) {
//            bookingRepository.saveAll(expiredBookings);
//        }
//    }
}

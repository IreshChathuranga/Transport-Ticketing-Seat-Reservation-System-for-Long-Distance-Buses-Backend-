package org.example.longdistancebusbackend.Util;


import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.TripSeat;
import org.example.longdistancebusbackend.repository.BookingRepository;
import org.example.longdistancebusbackend.repository.TripSeatRepository;
import org.springframework.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HeldSeatExpiryTask {
    private final TripSeatRepository tripSeatRepository;
    private final BookingRepository bookingRepository;

    @Scheduled(fixedRate = 60000)
    public void expireHeldSeats() {
        LocalDateTime now = LocalDateTime.now();

        // Expire TripSeats
        List<TripSeat> expiredSeats = tripSeatRepository.findByStatusAndHoldExpiresAtBefore("HELD", now);
        for (TripSeat ts : expiredSeats) {
            ts.setStatus("AVAILABLE");
            ts.setHoldExpiresAt(null);
        }
        tripSeatRepository.saveAll(expiredSeats);

        // Expire Bookings
        List<Booking> expiredBookings = bookingRepository.findByStatusAndExpiresAtBefore("HELD", now);
        for (Booking b : expiredBookings) {
            b.setStatus("EXPIRED");
        }
        bookingRepository.saveAll(expiredBookings);
    }
}

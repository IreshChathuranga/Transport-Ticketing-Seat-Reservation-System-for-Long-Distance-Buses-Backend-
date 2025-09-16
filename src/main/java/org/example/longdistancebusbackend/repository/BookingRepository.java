package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
//    List<Booking> findByStatusAndExpiresAtBefore(String status, LocalDateTime now);
    Optional<Booking> findByBookingRef(String bookingRef);
    List<Booking> findByStatusAndExpiresAtBefore(String status, LocalDateTime dateTime);
}

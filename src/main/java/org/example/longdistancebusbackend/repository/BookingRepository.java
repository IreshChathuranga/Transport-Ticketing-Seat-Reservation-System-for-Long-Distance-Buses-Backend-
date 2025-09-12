package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
//    List<Booking> findByStatusAndExpiresAtBefore(String status, LocalDateTime now);

    List<Booking> findByStatusAndExpiresAtBefore(String status, LocalDateTime dateTime);
}

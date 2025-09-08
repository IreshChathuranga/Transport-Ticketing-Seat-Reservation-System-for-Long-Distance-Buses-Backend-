package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}

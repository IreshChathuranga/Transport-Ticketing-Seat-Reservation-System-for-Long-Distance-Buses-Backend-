package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.TripSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripSeatRepository extends JpaRepository<TripSeat, Integer> {
    int countByTrip_TripIdAndStatus(Integer tripId, String status);
}

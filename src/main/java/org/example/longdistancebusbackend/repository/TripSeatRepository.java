package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.TripSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripSeatRepository extends JpaRepository<TripSeat, Integer> {
    int countByTrip_TripIdAndStatus(Integer tripId, String status);

    List<TripSeat> findByTrip_TripId(Integer tripId);
}

package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Seat;
import org.example.longdistancebusbackend.entity.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatMapRepository extends JpaRepository<SeatMap, Integer> {
}

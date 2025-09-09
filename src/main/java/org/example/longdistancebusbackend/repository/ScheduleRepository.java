package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("SELECT s FROM Schedule s WHERE s.route.originStop.name = :from AND s.route.destinationStop.name = :to AND FUNCTION('DATE', s.departTime) = :date")
    List<Schedule> findByRouteStopsAndDate(@Param("from") String from, @Param("to") String to, @Param("date") LocalDate date);

}

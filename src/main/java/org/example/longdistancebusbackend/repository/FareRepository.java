package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare, Integer> {
}

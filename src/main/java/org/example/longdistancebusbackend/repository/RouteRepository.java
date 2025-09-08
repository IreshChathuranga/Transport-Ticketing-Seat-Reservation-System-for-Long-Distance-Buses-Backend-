package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Route;
import org.example.longdistancebusbackend.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
}
